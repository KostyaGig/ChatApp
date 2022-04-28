package ru.zinoview.viewmodelmemoryleak.ui.chat.notification

import android.app.Notification
import android.util.Log
import ru.zinoview.viewmodelmemoryleak.ui.core.Show

interface NotificationWrapper : Show<NotificationService>,DisconnectNotification {

    data class Base(
        private val id: Int,
        private val notification: Notification
    ) : NotificationWrapper {

        override fun show(service: NotificationService) {
            val time = notification.extras.getCharSequence(TITLE_TEXT).toString()
            service.show(id,notification,time)
        }

        override fun disconnect(service: NotificationService, content: String) {
            val contentText = notification.extras.getCharSequence(CONTENT_TEXT)
            val time = notification.extras.getCharSequence(TITLE_TEXT).toString()
            if (content == contentText) {
                service.disconnect(id,time)
            }
        }

        private companion object {
            private const val CONTENT_TEXT = "android.text"
            private const val TITLE_TEXT = "android.title"
        }
    }

    object Empty : NotificationWrapper {
        override fun show(arg: NotificationService) = Unit
        override fun disconnect(service: NotificationService, content: String) = Unit
    }
}