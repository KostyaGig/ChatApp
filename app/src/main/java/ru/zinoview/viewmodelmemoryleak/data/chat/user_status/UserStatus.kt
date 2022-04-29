package ru.zinoview.viewmodelmemoryleak.data.chat.user_status

// todo remove
interface UserStatus {

    object Online : UserStatus

    object Offline : UserStatus

    object Empty : UserStatus
}