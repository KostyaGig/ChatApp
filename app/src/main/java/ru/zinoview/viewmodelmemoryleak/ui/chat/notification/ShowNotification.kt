package ru.zinoview.viewmodelmemoryleak.ui.chat.notification

import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudMessage
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Disconnect
import ru.zinoview.viewmodelmemoryleak.ui.core.Show

interface ShowNotification : Show<List<CloudMessage>>, Disconnect<String> {

    class Base(
        private val notification: NotificationWrappers,
        private val service: NotificationService,
    ) : ShowNotification {

        override fun show(messages: List<CloudMessage>) {
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