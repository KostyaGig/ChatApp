package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Disconnect
import ru.zinoview.viewmodelmemoryleak.ui.core.Show

interface MessagesNotification : Show<List<CloudMessage>>, Disconnect<String> {

    class Base(
        private val context: Context
    ) : MessagesNotification {

        private var notifications = mutableListOf<Pair<Int, Notification>>()

        // todo refactor
        override fun show(messages: List<CloudMessage>) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val notificationChannel = NotificationChannel(
                    "LOLIK", "Process messages",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "desc"
                }
                notificationManager.createNotificationChannel(notificationChannel)
            }

            messages.forEachIndexed { index, cloudMessage ->
                cloudMessage.mapToNotification(notifications, index,context)
            }

            notifications.forEach { pair ->
                NotificationManagerCompat.from(context).notify(pair.first,pair.second)
            }

        }

        // todo refactor

        override fun disconnect(messageContent: String) {
            notifications.forEach { pair ->
                val notificationId = pair.first
                val extras = pair.second.extras
                val contentText = extras.getCharSequence("android.text")

                Log.d("zinoviewk","DISCONNECT BY CONTENT $contentText")

                if (contentText == messageContent) {
                    NotificationManagerCompat.from(context).cancel(notificationId)
                }
            }
        }
    }

    object Empty : MessagesNotification {
        override fun show(arg: List<CloudMessage>) = Unit
        override fun disconnect(arg: String) = Unit

    }
}