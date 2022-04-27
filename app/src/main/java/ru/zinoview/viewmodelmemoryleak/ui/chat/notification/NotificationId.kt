package ru.zinoview.viewmodelmemoryleak.ui.chat.notification

interface NotificationId {

    fun id() : Int

    class Base : NotificationId {

        private var id = 0

        override fun id() = ++id
    }
}