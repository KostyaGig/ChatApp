package ru.zinoview.viewmodelmemoryleak.data.chat.user_status.cloud

interface CloudStatusUser {

    class Base(
        private val notificationToken: String
    ) : CloudStatusUser
}