package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

import io.socket.client.Socket
import ru.zinoview.viewmodelmemoryleak.core.chat.EditMessage
import ru.zinoview.viewmodelmemoryleak.core.chat.ShowProcessingMessages
import ru.zinoview.viewmodelmemoryleak.data.chat.SendMessage
import ru.zinoview.viewmodelmemoryleak.data.chat.ui_state.UiStateSharedPreferences
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.AbstractCloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Disconnect
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Json
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.SocketConnection
import ru.zinoview.viewmodelmemoryleak.ui.chat.ReadMessages
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.UiStates

interface CloudDataSource<T> : Disconnect<Unit>, SendMessage, EditMessage, ReadMessages {

    suspend fun messages(block:(List<CloudMessage>) -> Unit) : T
    override fun disconnect(arg: Unit) = Unit
    override fun readMessages(data: Pair<Int, Int>) = Unit
    fun saveMessages(prefs: UiStateSharedPreferences,state: UiStates) = Unit

    class Base(
        private val socket: Socket,
        private val connection: SocketConnection,
        private val json: Json,
        private val data: Data<List<CloudMessage>>,
        private val messagesStore: MessagesStore,
        private val processingMessages: ProcessingMessages
    ) : AbstractCloudDataSource.Base(socket, connection), CloudDataSource<Unit> {

        override suspend fun messages(block:(List<CloudMessage>) -> Unit) {
            connection.connect(socket)
            messagesStore.subscribe(block)

            socket.on(MESSAGES) { cloudData ->
                val wrapperMessages = json.json(cloudData.first())
                val modelMessages = json.objectFromJson(wrapperMessages,WrapperMessages::class.java).map()

                val messages = data.data(modelMessages)

                processingMessages.update(messages)

                messagesStore.addMessages(messages)
            }

            socket.emit(MESSAGES)
            connection.addSocketBranch(MESSAGES)
        }

        override suspend fun sendMessage(userId: String,content: String) {
            val message = json.json(
                Pair(
                    SENDER_ID_KEY,userId
                ),
                Pair(
                    MESSAGE_CONTENT_KEY,content
                )
            )

            connection.connect(socket)
            socket.emit(SEND_MESSAGE,message)
        }

        override suspend fun editMessage(messageId: String, content: String) {
            val message = json.json(
                Pair(
                    MESSAGE_ID_KEY,messageId
                ),
                Pair(
                    MESSAGE_CONTENT_KEY,content
                )
            )

            connection.connect(socket)
            connection.addSocketBranch(EDIT_MESSAGE)


            socket.emit(EDIT_MESSAGE,message)
        }

        override fun readMessages(range: Pair<Int, Int>) {
            messagesStore.unreadMessageIds(range) { unreadMessageIds ->
                val ids = CloudUnreadMessageIds.Base(unreadMessageIds)
                val jsonIds = json.json(ids)

                socket.emit(UPDATE_MESSAGE,jsonIds)
            }
            connection.addSocketBranch(UPDATE_MESSAGE)
            connection.connect(socket)
        }

        override fun disconnect(arg: Unit)  = Unit
    }

    private companion object {
        private const val SEND_MESSAGE = "send_message"
        private const val MESSAGES = "messages"
        private const val EDIT_MESSAGE = "edit_message"
        private const val UPDATE_MESSAGE = "update_message"

        private const val SENDER_ID_KEY = "senderId"
        private const val MESSAGE_CONTENT_KEY = "content"
        private const val MESSAGE_ID_KEY = "id"
    }

    class Update(
        private val messagesStore: MessagesStore,
        private val processingMessages: ProcessingMessages
        ) : CloudDataSource<Unit>,
            ShowProcessingMessages {

        override suspend fun sendMessage(userId: String, content: String) {
            val progressMessage = CloudMessage.Progress.Send(
                userId,
                content,
                System.currentTimeMillis().toString()
            )

            messagesStore.add(progressMessage)
            processingMessages.add(progressMessage)
        }

        override suspend fun editMessage(messageId: String, content: String) {
            messagesStore.updateProcessingMessages(processingMessages,messageId, content)
            messagesStore.editMessage(messageId, content)
        }

        override fun showProcessingMessages() = processingMessages.show(Unit)

        override suspend fun messages(block: (List<CloudMessage>) -> Unit) = Unit
    }

    class Test : CloudDataSource<List<CloudMessage>> {

        private val messages = mutableListOf<CloudMessage.Test>()
        private var isSuccess = false

        override suspend fun sendMessage(userId: String, content: String) {
            messages.add(CloudMessage.Test(
                "-1",userId,content,false,"-1"
            ))
        }

        override suspend fun messages(block: (List<CloudMessage>) -> Unit) : List<CloudMessage> {
            val result = if (isSuccess) {
                messages
            } else {
                listOf(CloudMessage.Failure("Messages are empty"))
            }
            isSuccess = !isSuccess
            return result
        }

        override fun readMessages(range: Pair<Int, Int>) {
            for (index in range.first..range.second) {
                val message = messages[index]
                messages[index] = message.read()
            }
        }

        override fun disconnect(arg: Unit) = messages.clear()

        override suspend fun editMessage(messageId: String, content: String) {
            val message = messages[messageId.toInt() - 1]
            messages[messageId.toInt() - 1]  = message.updated(content)
        }

    }

}