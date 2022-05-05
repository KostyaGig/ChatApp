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
import ru.zinoview.viewmodelmemoryleak.ui.chat.notification.NotificationService

interface CloudDataSource<T> : Messages<CloudMessage>, Disconnect<Unit>, SendMessage, EditMessage, ReadMessages {

    override fun readMessages(range: Pair<Int, Int>) = Unit

    override suspend fun messages(block: (List<CloudMessage>) -> Unit) = Unit

    // todo test
    suspend fun updateTypeMessageState(isTyping: Boolean,senderNickName: String) = Unit

    class Base(
        private val socket: Socket,
        private val connection: SocketConnection,
        private val json: Json,
        private val data: Data<List<CloudMessage>>,
        private val messagesStore: MessagesStore,
        private val processingMessages: ProcessingMessages,
        private val notificationService: NotificationService
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
                    SENDER_NICK_NAME_KEY,nickName
                ),
                Pair(
                    MESSAGE_CONTENT_KEY,content
                ),
            )

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

            connection.addSocketBranch(EDIT_MESSAGE)

            socket.emit(EDIT_MESSAGE,message)
        }

        override fun readMessages(range: Pair<Int, Int>) {
            messagesStore.unreadMessageIds(range) { unreadMessageIds ->
                val ids = CloudUnreadMessageIds.Base(unreadMessageIds)
                val jsonIds = json.json(ids)

                socket.emit(READ_MESSAGE,jsonIds)

                unreadMessageIds.forEach { tag ->
                    notificationService.disconnect(tag)
                }
            }
            connection.addSocketBranch(READ_MESSAGE)
        }

        override suspend fun updateTypeMessageState(isTyping: Boolean,senderNickName: String) {
            connection.connect(socket)

            val state = json.json(
                Pair(
                    IS_TYPING_KEY,isTyping
                ),
                Pair(
                    SENDER_NICK_NAME_KEY,senderNickName
                )
            )

//            socket.on(TO_TYPE_MESSAGE) { cloudData ->
//                Log.d("zinoviewk","scoket to type")
//                if (cloudData != null) {
//                    val jsonTypingMessage = json.json(cloudData.first())
//                    val typingMessage = json.objectFromJson(jsonTypingMessage,TypingValue::class.java).map()
//
//                    Log.d("zinoviewk","data - $typingMessage")
//
//                    if (typingMessage.isCloud()) {
//                        Log.d("zinoviewk","isCloud")
//                    } else {
//                        Log.d("zinoviewk","isNotCloud")
//                    }
//
////                    messagesStore.add(typingMessage)
////                    messagesStore.remove(typingMessage)
//                } else {
//                    Log.d("zinoviewk","data is nulll")
//                }
//            }

            socket.emit(TO_TYPE_MESSAGE,state)
            connection.addSocketBranch(TO_TYPE_MESSAGE)
        }

        override fun disconnect(arg: Unit)  = Unit
    }

    private companion object {
        private const val SEND_MESSAGE = "send_message"
        private const val MESSAGES = "messages"
        private const val EDIT_MESSAGE = "edit_message"
        private const val READ_MESSAGE = "read_message"
        private const val TO_TYPE_MESSAGE = "to_type_message"

        private const val SENDER_ID_KEY = "senderId"
        private const val SENDER_NICK_NAME_KEY = "senderNickName"
        private const val MESSAGE_CONTENT_KEY = "content"
        private const val MESSAGE_ID_KEY = "id"
        private const val IS_TYPING_KEY = "isTyping"
    }

}