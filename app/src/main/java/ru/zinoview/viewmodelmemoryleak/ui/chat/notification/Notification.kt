package ru.zinoview.viewmodelmemoryleak.ui.chat.notification

import android.content.Context
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudMessage
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Disconnect


interface Notification : CreateChannel, Disconnect<String> {

    fun notifications(messages: List<CloudMessage>) : List<NotificationWrapper>

    class Base(
        private val channel: Channel,
        private val mapper: NotificationMapper,
        private val context: Context
    ) : Notification {

        private val notifications = ArrayList<NotificationWrapper>()

        override fun notifications(messages: List<CloudMessage>): List<NotificationWrapper> {
            val notifications = messages.map { messages -> messages.mapNotification(mapper) }

            this.notifications.clear()
            this.notifications.addAll(notifications)

            return notifications
        }

        override fun disconnect(content: String) {
            notifications.forEach { notifcation ->
                notifcation.disconnect(Pair(context,content))
            }
        }

        override fun createChannel() = channel.createChannel()
    }
}