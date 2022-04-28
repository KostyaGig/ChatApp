package ru.zinoview.viewmodelmemoryleak.ui.chat.notification

import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudMessage


interface Notification : CreateChannel, DisconnectNotification {

    fun notifications(messages: List<CloudMessage>) : List<NotificationWrapper>

    class Base(
        private val channel: Channel,
        private val mapper: NotificationMapper
    ) : Notification {

        private val notifications = ArrayList<NotificationWrapper>()

        override fun notifications(messages: List<CloudMessage>): List<NotificationWrapper> {
            val notifications = messages.map { messages -> messages.mapNotification(mapper) }

            this.notifications.addAll(notifications)

            return notifications
        }

        override fun disconnect(service: NotificationService, content: String) {
            notifications.forEach { notifcation ->
                notifcation.disconnect(service, content)
            }
        }

        override fun createChannel() = channel.createChannel()
    }
}