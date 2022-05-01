package ru.zinoview.viewmodelmemoryleak.ui.chat.notification

import android.app.Notification
import ru.zinoview.viewmodelmemoryleak.ui.core.Show

interface NotificationWrapper : Show<NotificationService>,DisconnectNotification {

    data class Base(
        private val id: Int,
        private val notification: Notification,
        private val time: String
    ) : NotificationWrapper {

        override fun show(service: NotificationService)
            = service.show(notification,time,id)

        override fun disconnect(service: NotificationService, content: String) {
            val contentText = notification.extras.getCharSequence(CONTENT_TEXT)
            if (content == contentText) {
                service.disconnect(time,id)
            }
        }

        private companion object {
            private const val CONTENT_TEXT = "android.text"
        }
    }

    object Empty : NotificationWrapper {
        override fun show(arg: NotificationService) = Unit
        override fun disconnect(service: NotificationService, content: String) = Unit
    }
}