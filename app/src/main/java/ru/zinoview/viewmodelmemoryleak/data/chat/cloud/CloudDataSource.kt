package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

import io.socket.client.Socket
import ru.zinoview.viewmodelmemoryleak.core.chat.EditMessage
import ru.zinoview.viewmodelmemoryleak.core.chat.Messages
import ru.zinoview.viewmodelmemoryleak.data.chat.SendMessage
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.AbstractCloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Disconnect
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Json
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.SocketConnection
import ru.zinoview.viewmodelmemoryleak.ui.chat.ReadMessages

interface CloudDataSource<T> : Messages<CloudMessage>, Disconnect<Unit>, SendMessage, EditMessage, ReadMessages {

    override fun readMessages(range: Pair<Int, Int>) = Unit

    override suspend fun messages(block: (List<CloudMessage>) -> Unit) = Unit

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

        override suspend fun sendMessage(userId: String,nickName: String,content: String) {
            val message = json.json(
                Pair(
                    SENDER_ID_KEY,userId
                ),
                Pair(
                    SENDER_NICK_NAME,nickName
                ),
                Pair(
                    MESSAGE_CONTENT_KEY,content
                ),
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

                socket.emit(READ_MESSAGE,jsonIds)
            }
            connection.addSocketBranch(READ_MESSAGE)
            connection.connect(socket)
        }

        override fun disconnect(arg: Unit)  = Unit
    }

    private companion object {
        private const val SEND_MESSAGE = "send_message"
        private const val MESSAGES = "messages"
        private const val EDIT_MESSAGE = "edit_message"
        private const val READ_MESSAGE = "read_message"

        private const val SENDER_ID_KEY = "senderId"
        private const val SENDER_NICK_NAME = "senderNickName"
        private const val MESSAGE_CONTENT_KEY = "content"
        private const val MESSAGE_ID_KEY = "id"
    }

}