package ru.zinoview.viewmodelmemoryleak.data.chat.notification.cloud

import android.content.Context
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.ui.chat.notification.NotificationMessageActivity
import ru.zinoview.viewmodelmemoryleak.ui.chat.notification.NotificationMessageService
import ru.zinoview.viewmodelmemoryleak.ui.chat.notification.NotificationWrapper
import ru.zinoview.viewmodelmemoryleak.ui.core.MainActivity

interface CloudMessage {

    fun notification(context: Context) : NotificationWrapper

    class Base(
        private val id: String,
        private val senderNickName: String,
        private val content: String
    ) : CloudMessage {

        override fun notification(context: Context) : NotificationWrapper {

            val baseNotificationBuilder = ru.zinoview.viewmodelmemoryleak.ui.chat.notification.Notification.Base(context)
            val notification = baseNotificationBuilder.notification(senderNickName,content,R.drawable.ic_send_notification,
                CHANNEL,"")

            val intentNotificationBuilder = ru.zinoview.viewmodelmemoryleak.ui.chat.notification.Notification.Intent(notification)
            val intentNotification = intentNotificationBuilder.intentNotification(context, NotificationMessageService::class.java,id)

            return NotificationWrapper.Push(id,intentNotification.build())
        }

        private companion object {
            private const val CHANNEL = "Push messages"
        }
    }
}