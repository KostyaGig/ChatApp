package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

import com.google.gson.Gson
import io.socket.client.Socket
import ru.zinoview.viewmodelmemoryleak.core.chat.EditMessage
import ru.zinoview.viewmodelmemoryleak.core.chat.UpdateMessagesState
import ru.zinoview.viewmodelmemoryleak.data.chat.SendMessage
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.AbstractCloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Disconnect
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Json
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.SocketConnection

interface CloudDataSource<T> : Disconnect<Unit>,
    SendMessage,EditMessage, UpdateMessagesState {

    suspend fun messages(block:(List<CloudMessage>) -> Unit) : T

    class Base(
        private val socket: Socket,
        private val connection: SocketConnection,
        private val json: Json,
        private val gson: Gson,
        private val data: Data<List<CloudMessage>>,
        private val messagesStore: MessagesStore
    ) : AbstractCloudDataSource.Base(socket, connection), CloudDataSource<Unit> {

        override suspend fun messages(block:(List<CloudMessage>) -> Unit) {
            connection.connect(socket)
            messagesStore.subscribe(block)

            socket.on(MESSAGES) { cloudData ->
                val wrapperMessages = gson.toJson(cloudData.first())
                val modelMessages = gson.fromJson(wrapperMessages, WrapperMessages::class.java).map()

                val messages = data.data(modelMessages)

                messagesStore.addMessages(messages)
            }

            socket.emit(MESSAGES)
            connection.addSocketBranch(MESSAGES)
        }

        override suspend fun sendMessage(userId: String,content: String) {
            val message = json.create(
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
            val message = json.create(
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

        override fun updateMessagesState(range: Pair<Int, Int>) {
            messagesStore.unreadMessageIds(range) { unreadMessageIds ->
                val ids = CloudUnreadMessageIds.Base(unreadMessageIds)
                val jsonIds = gson.toJson(ids)

                socket.emit(UPDATE_MESSAGE,jsonIds)
            }
            connection.addSocketBranch(UPDATE_MESSAGE)
            connection.connect(socket)
        }

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

    class Update(private val messagesStore: MessagesStore) : CloudDataSource<Unit> {

        override suspend fun sendMessage(userId: String, content: String) {
            val progressMessage = CloudMessage.Progress(
                userId,
                content
            )

            messagesStore.addMessage(progressMessage)
        }

        override suspend fun editMessage(messageId: String, content: String)
            = messagesStore.editMessage(messageId, content)


        override fun updateMessagesState(range: Pair<Int, Int>) = Unit
        override suspend fun messages(block: (List<CloudMessage>) -> Unit) = Unit
        override fun disconnect(arg: Unit) = Unit

    }

    class Test : CloudDataSource<List<CloudMessage>> {

        private val messages = mutableListOf<CloudMessage.Test>()
        private var isSuccess = false

        // todo test check
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

        override fun updateMessagesState(range: Pair<Int, Int>) {
            for (index in range.first..range.second) {
                val message = messages[index]
                messages[index] = message.read()
            }
        }

        override fun disconnect(arg: Unit) = messages.clear()

        override suspend fun editMessage(messageId: String, content: String) {
            val message = messages[messageId.toInt() - 1]
            messages[messageId.toInt() - 1]  = message.update(content)
        }

    }

}