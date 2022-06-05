package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

import ru.zinoview.viewmodelmemoryleak.core.chat.ShowNotificationMessage
import ru.zinoview.viewmodelmemoryleak.core.cloud.SocketData
import ru.zinoview.viewmodelmemoryleak.core.cloud.SocketWrapper
import ru.zinoview.viewmodelmemoryleak.data.chat.SendMessage
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.AbstractCloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Disconnect
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Json
import ru.zinoview.viewmodelmemoryleak.core.chat.ReadMessage
import ru.zinoview.viewmodelmemoryleak.core.chat.ReadMessages
import ru.zinoview.viewmodelmemoryleak.core.chat.Subscribe
import ru.zinoview.viewmodelmemoryleak.ui.chat.notification.NotificationService

interface CloudDataSource<T> : Disconnect<Unit>, SendMessage, ReadMessage, ShowNotificationMessage,Subscribe {

    suspend fun messages(senderId: String,receiverId: String,block: (List<CloudMessage>) -> Unit) = Unit

    suspend fun editMessage(
        messageId: String,
        content: String,
        senderId: String,
        receiverId: String
    )

    suspend fun toTypeMessage(isTyping: Boolean,senderNickName: String) : T

    // todo test
    override suspend fun showNotificationMessage(messageId: String) = Unit

    class Base(
        private val socketWrapper: SocketWrapper,
        private val json: Json,
        private val data: Data<List<CloudMessage>>,
        private val messagesStore: MessagesStore,
        private val processingMessages: ProcessingMessages,
        private val notificationService: NotificationService
    ) : AbstractCloudDataSource.Base(socketWrapper), CloudDataSource<Unit> {

        override fun subscribeToChanges() {
            socketWrapper.subscribe(TO_TYPE_MESSAGE) { cloudData ->
                val jsonTypingMessage = json.json(cloudData.first())
                val typingMessage = json.objectFromJson(jsonTypingMessage,TypingMessageValue::class.java).map()


                messagesStore.add(typingMessage)
                messagesStore.remove(typingMessage)
            }

            socketWrapper.subscribe(MESSAGES) {cloudData ->
                val wrapperMessages = json.json(cloudData.first())
                val modelMessages = json.objectFromJson(wrapperMessages,CloudMessagesDataWrapper::class.java).map()

                val messages = data.data(modelMessages)

                processingMessages.update(messages)

                messagesStore.addMessages(messages)
            }
        }

        override suspend fun messages(senderId: String, receiverId: String, block:(List<CloudMessage>) -> Unit) {
            messagesStore.subscribe(block)

            val json = json.json(
                Pair(
                    SENDER_ID_KEY,senderId
                ),
                Pair(
                    RECEIVER_ID_KEY,receiverId
                )
            )
            socketWrapper.emit(MESSAGES,SocketData.Base(json))
        }

        override suspend fun sendMessage(senderId: String, receiverId: String,nickName: String, content: String) {
            val data = SocketData.Base(json.json(
                Pair(
                    SENDER_ID_KEY,senderId
                ),
                Pair(
                    RECEIVER_ID_KEY,receiverId
                ),
                Pair(
                    SENDER_NICK_NAME_KEY,nickName
                ),
                Pair(
                    MESSAGE_CONTENT_KEY,content
                ),
            ))

            socketWrapper.emit(SEND_MESSAGE,data)
        }

        override suspend fun editMessage(
            messageId: String,
            content: String,
            senderId: String,
            receiverId: String
        ) {
            val data = SocketData.Base(json.json(
                Pair(
                    MESSAGE_ID_KEY,messageId
                ),
                Pair(
                    MESSAGE_CONTENT_KEY,content
                ),
                Pair(
                    SENDER_ID_KEY,senderId
                ),
                Pair(
                    RECEIVER_ID_KEY,receiverId
                )
            ))

            socketWrapper.emit(EDIT_MESSAGE,data)
        }

        override fun readMessages(readMessages: ReadMessages) {
            socketWrapper.connect(READ_MESSAGE)
            val unreadMessageIds = readMessages.unreadMessageIds(messagesStore)

            val data = readMessages.json(json,messagesStore)

            socketWrapper.emit(READ_MESSAGE,data)

            unreadMessageIds.forEach { tag ->
                notificationService.disconnect(tag)
            }
        }

        override suspend fun toTypeMessage(isTyping: Boolean,senderNickName: String) {
            val data = SocketData.Base(json.json(
                Pair(
                    IS_TYPING_KEY,isTyping
                ),
                Pair(
                    SENDER_NICK_NAME_KEY,senderNickName
                )
            ))

            socketWrapper.emit(TO_TYPE_MESSAGE,data)
        }

        override suspend fun showNotificationMessage(messageId: String) {
            val data = SocketData.Base(
                json.json(
                    Pair(NOTIFICATION_MESSAGE_ID_KEY,messageId)
                )
            )
            socketWrapper.emit(SHOW_NOTIFICATION_MESSAGE,data)
        }

        override fun disconnect(arg: Unit)  = Unit
    }

    private companion object {
        private const val SEND_MESSAGE = "send_message"
        private const val MESSAGES = "messages"
        private const val EDIT_MESSAGE = "edit_message"
        private const val READ_MESSAGE = "read_message"
        private const val TO_TYPE_MESSAGE = "to_type_message"
        private const val SHOW_NOTIFICATION_MESSAGE = "show_notification_message"

        private const val SENDER_ID_KEY = "senderId"
        private const val RECEIVER_ID_KEY = "receiverId"
        private const val SENDER_NICK_NAME_KEY = "senderNickName"
        private const val MESSAGE_CONTENT_KEY = "content"
        private const val MESSAGE_ID_KEY = "id"
        private const val IS_TYPING_KEY = "isTyping"
        private const val NOTIFICATION_MESSAGE_ID_KEY = "messageId"

    }

}