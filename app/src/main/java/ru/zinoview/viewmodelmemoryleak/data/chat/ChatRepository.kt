package ru.zinoview.viewmodelmemoryleak.data.chat

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.core.Clean
import ru.zinoview.viewmodelmemoryleak.core.chat.*
import ru.zinoview.viewmodelmemoryleak.core.chat.SendMessage
import ru.zinoview.viewmodelmemoryleak.data.cache.UserSharedPreferences
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudToDataMessageMapper
import ru.zinoview.viewmodelmemoryleak.data.core.CleanRepository
import ru.zinoview.viewmodelmemoryleak.core.chat.ReadMessage

interface ChatRepository<T> : Messages<DataMessage>, SendMessage, EditMessage,
    ReadMessage, ToTypeMessage<T>, ShowNotificationMessage, ShowProcessingMessages, Clean {

    override fun showProcessingMessages() = Unit

    override suspend fun messages(receiverId: String, block: (List<DataMessage>) -> Unit) = Unit

    class Base(
        private val cloudDataSource: CloudDataSource<Unit>,
        private val mapper: CloudToDataMessageMapper,
        private val userSharedPreferences: UserSharedPreferences,
        private val chatActions: ChatActions
    ) : ChatRepository<Unit>,
        CleanRepository(cloudDataSource) {

        override suspend fun sendMessage(receiverId: String, content: String) {
            val senderId = userSharedPreferences.id()
            val userNickName = userSharedPreferences.nickName()

            val data = listOf(senderId, receiverId, userNickName, content)

            chatActions.sendMessage(data)
        }

        override suspend fun editMessage(messageId: String, content: String, receiverId: String) {
            val senderId = userSharedPreferences.id()
            val data = listOf(messageId, content, senderId, receiverId)

            chatActions.editMessage(data)
        }

        override suspend fun toTypeMessage(isTyping: Boolean) {
            val userNickName = userSharedPreferences.nickName()
            cloudDataSource.toTypeMessage(isTyping, userNickName)
        }

        override suspend fun showNotificationMessage(messageId: String) =
            cloudDataSource.showNotificationMessage(messageId)

        override fun readMessages(readMessages: ReadMessages) {
            val dataReadMessages = readMessages.dataReadMessages(userSharedPreferences.id())
            cloudDataSource.readMessages(dataReadMessages)
        }

        override suspend fun messages(receiverId: String, block: (List<DataMessage>) -> Unit) {
            cloudDataSource.messages(userSharedPreferences.id(), receiverId) { cloud ->
                val data = cloud.map { it.map(mapper) }
                block.invoke(data)
            }
        }

        override fun showProcessingMessages() = Unit
    }

}