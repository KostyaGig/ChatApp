package ru.zinoview.viewmodelmemoryleak.data.chat.processing_messages

import ru.zinoview.viewmodelmemoryleak.core.chat.EditMessage
import ru.zinoview.viewmodelmemoryleak.core.chat.SendMessage
import ru.zinoview.viewmodelmemoryleak.data.cache.UserSharedPreferences
import ru.zinoview.viewmodelmemoryleak.data.chat.processing_messages.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.ui.core.Show

interface ProcessingChatMessagesRepository : SendMessage,EditMessage, Show<Unit> {

    class Base(
        private val cloudDataSource: CloudDataSource,
        private val userSharedPreferences: UserSharedPreferences
    ) : ProcessingChatMessagesRepository {

        override suspend fun sendMessage(receiverId: String,content: String) {
            val id = userSharedPreferences.id()
            val nickName = userSharedPreferences.nickName()

            cloudDataSource.sendMessage(id,nickName,content)
        }

        override suspend fun editMessage(messageId: String, content: String, receiverId: String)
            = cloudDataSource.editMessage(messageId, content,receiverId)


        override fun show(arg: Unit) = cloudDataSource.show(arg)
    }
}