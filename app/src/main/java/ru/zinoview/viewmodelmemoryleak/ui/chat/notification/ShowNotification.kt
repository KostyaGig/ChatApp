package ru.zinoview.viewmodelmemoryleak.ui.chat.notification

import android.os.Build
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudMessage
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Disconnect
import ru.zinoview.viewmodelmemoryleak.ui.core.Show

interface ShowNotification : Show<List<CloudMessage>>, Disconnect<String> {

    class Base(
        private val notification: Notification,
        private val service: NotificationService,
    ) : ShowNotification {

        override fun show(messages: List<CloudMessage>) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notification.createChannel()
            }

            val notifications = notification.notifications(messages)

            notifications.forEach { notification ->
                notification.show(service)
            }
        }

        override fun disconnect(content: String) = notification.disconnect(service, content)
    }

    object Empty : ShowNotification {
        override fun show(arg: List<CloudMessage>) = Unit
        override fun disconnect(arg: String) = Unit
    }

}