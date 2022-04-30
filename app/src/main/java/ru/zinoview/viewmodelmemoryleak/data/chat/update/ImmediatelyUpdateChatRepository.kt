package ru.zinoview.viewmodelmemoryleak.data.chat.update

import ru.zinoview.viewmodelmemoryleak.core.chat.EditMessage
import ru.zinoview.viewmodelmemoryleak.core.chat.SendMessage
import ru.zinoview.viewmodelmemoryleak.data.cache.UserSharedPreferences
import ru.zinoview.viewmodelmemoryleak.data.chat.update.cloud.CloudDataSource

interface ImmediatelyUpdateChatRepository : SendMessage, EditMessage {


    class Base(
        private val cloudDataSource: CloudDataSource,
        private val prefs: UserSharedPreferences
    ) : ImmediatelyUpdateChatRepository {


        override suspend fun sendMessage(content: String) {
            // todo make id() -> String
            val id = prefs.id().toString()
            val nickName = prefs.nickName()
            cloudDataSource.sendMessage(id,nickName,content)
        }

        override suspend fun editMessage(messageId: String, content: String)
            = cloudDataSource.editMessage(messageId, content)
    }
}