package ru.zinoview.viewmodelmemoryleak.ui.chat.notification

import android.content.Context
import androidx.core.app.NotificationCompat
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.core.Date
import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider

interface UniqueNotification {

    fun notification(iconRes: Int, content: String,groupId: String,time: String) : NotificationWrapper

    class Base(
        private val context: Context,
        private val resourceProvider: ResourceProvider,
        private val notificationId: NotificationId,
        private val date: Date
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

                val notification = NotificationCompat.Builder(context, groupId)
                    .setContentTitle(resourceProvider.string(R.string.waiting_for_network))
                    .setContentText(content)
                    .setSmallIcon(iconRes)
                    .setContentTitle(date.date(time))
                    .setGroup(groupId)
                    .build()

                NotificationWrapper.Base(notificationId.id(), notification)
            } else {
                NotificationWrapper.Empty
            }
        }

    }
}