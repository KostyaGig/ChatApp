package ru.zinoview.viewmodelmemoryleak.ui.chat.notification

import ru.zinoview.viewmodelmemoryleak.R
interface NotificationMapper {

    fun mapSend(content: String) : NotificationWrapper = NotificationWrapper.Empty

    fun mapEdit(content: String) : NotificationWrapper = NotificationWrapper.Empty

    class Base(
        private val channelId: GroupId
    ) : NotificationMapper {

        override fun mapEdit(content: String)
            = channelId.editMessageNotification(R.drawable.ic_edit_notification,content)

        override fun mapSend(content: String)
            = channelId.sendMessageNotification(R.drawable.ic_send_notification,content)
    }
}