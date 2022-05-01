package ru.zinoview.viewmodelmemoryleak.ui.chat.notification

import ru.zinoview.viewmodelmemoryleak.R
interface NotificationMapper {

    fun mapSend(content: String,time: String) : NotificationWrapper = NotificationWrapper.Empty

    fun mapEdit(content: String,time: String) : NotificationWrapper = NotificationWrapper.Empty

    class Base(
        private val channelId: Channel
    ) : NotificationMapper {

        override fun mapEdit(content: String,time: String)
            = channelId.editMessageNotification(R.drawable.ic_edit_notification,content,time)

        override fun mapSend(content: String,time: String)
            = channelId.sendMessageNotification(R.drawable.ic_send_notification,content,time)
    }
}