package ru.zinoview.viewmodelmemoryleak.ui.chat.notification

import ru.zinoview.viewmodelmemoryleak.R
interface NotificationMapper {

    fun mapSend(content: String) : NotificationWrapper = NotificationWrapper.Empty

    fun mapEdit(content: String) : NotificationWrapper = NotificationWrapper.Empty

    class Base(
        private val channel: Channel
    ) : NotificationMapper {

        override fun mapEdit(content: String)
            = channel.notification(R.drawable.ic_edit_notification,content)

        override fun mapSend(content: String)
            = channel.notification(R.drawable.ic_send_notification,content)
    }
}