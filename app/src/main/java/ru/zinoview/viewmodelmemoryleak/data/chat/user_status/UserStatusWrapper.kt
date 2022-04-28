package ru.zinoview.viewmodelmemoryleak.data.chat.user_status

import ru.zinoview.viewmodelmemoryleak.core.Update

interface UserStatusWrapper : Update<UserStatus> {

    // todo remove later
    fun isOnline() : Boolean

    class Base : UserStatusWrapper {

        private var status: UserStatus = UserStatus.Empty

        override fun update(status: UserStatus) {
            this.status = status
        }

        override fun isOnline(): Boolean {
            return status is UserStatus.Online
        }

    }
}