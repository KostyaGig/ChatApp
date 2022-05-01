package ru.zinoview.viewmodelmemoryleak.ui.chat.notification

import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.core.Date
import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider

interface UniqueNotification {

    fun notification(iconRes: Int, content: String,groupId: String,time: String) : NotificationWrapper

    class Base(
        private val resourceProvider: ResourceProvider,
        private val notificationId: NotificationId,
        private val date: Date,
        private val notification: Notification
    ) : UniqueNotification {

        private val notificationIds = ArrayList<String>()

        override fun notification(
            iconRes: Int,
            content: String,
            groupId: String,
            time: String
        ): NotificationWrapper {
            return if (notificationIds.contains(time).not()) {
                notificationIds.add(time)

                val notification = notification.notification(
                    resourceProvider.string(R.string.waiting_for_network),
                    content,
                    iconRes,
                    groupId,
                    date.date(time),
                )

                NotificationWrapper.Base(notificationId.id(), notification.build(),time)
            } else {
                NotificationWrapper.Empty
            }
        }

    }
}