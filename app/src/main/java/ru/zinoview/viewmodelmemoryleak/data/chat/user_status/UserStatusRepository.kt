package ru.zinoview.viewmodelmemoryleak.data.chat.user_status

import ru.zinoview.viewmodelmemoryleak.core.chat.user_status.Offline
import ru.zinoview.viewmodelmemoryleak.core.chat.user_status.Online

interface UserStatusRepository : Online, Offline {

    class Base(
        private val userStatus: UserStatusWrapper
    ) : UserStatusRepository {

        override fun online() = userStatus.update(UserStatus.Online)
        override fun offline() = userStatus.update(UserStatus.Offline)

    }
}