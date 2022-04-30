package ru.zinoview.viewmodelmemoryleak.data.chat.user_status

import ru.zinoview.viewmodelmemoryleak.core.Clean
import ru.zinoview.viewmodelmemoryleak.core.chat.user_status.Offline
import ru.zinoview.viewmodelmemoryleak.core.chat.user_status.Online

interface UserStatusRepository : Online, Offline, Clean {

    class Base(
        private val cloudDataSource: ru.zinoview.viewmodelmemoryleak.data.chat.user_status.cloud.CloudDataSource
    ) : UserStatusRepository {

        override suspend fun online() = cloudDataSource.online()
        override suspend fun offline() = cloudDataSource.offline()

        override fun clean() = Unit
    }
}