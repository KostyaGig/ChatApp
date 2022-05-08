package ru.zinoview.viewmodelmemoryleak.ui.chat.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import java.lang.IllegalStateException

interface Notification {

    fun notification(title: String,content: String,icon: Int,channelId: String,subText: String) : NotificationCompat.Builder

    // todo move intent data into a class
    fun <T> intentNotification(context: Context, activity: Class<T>,messageId: String) : NotificationCompat.Builder

    class Base(
        private val context: Context
    ) : Notification {

        override fun notification(
            title: String,
            content: String,
            icon: Int,
            channelId: String,
            subText: String
        ) : NotificationCompat.Builder {

            return NotificationCompat.Builder(context, channelId)
                .setContentText(content)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setGroup(channelId)
                .setSubText(subText)
        }

        override fun <T> intentNotification(
            context: Context,
            activity: Class<T>,
            messageId: String
        ) = throw IllegalStateException("Base.notificationIntent()")
    }

    class Intent(
        private val notification: NotificationCompat.Builder
    ) : Notification {

        override fun notification(
            title: String,
            content: String,
            icon: Int,
            channelId: String,
            subText: String
        ) : NotificationCompat.Builder = throw IllegalStateException("Push.notification()")

        override fun <T> intentNotification(context: Context, activity: Class<T>,messageId: String) : NotificationCompat.Builder {
            // todo move to class and use it in mainActivity before launch mainactivity
            val intent = Intent(context, activity).apply {
                putExtra("fragment_key","chat")
                putExtra("message_id",messageId)
            }

            intent.flags = android.app.Notification.FLAG_AUTO_CANCEL or android.app.Notification.FLAG_ONGOING_EVENT

            // TODO CHECK UPDATE CURRENT AND ANOTHER WAYS
            val contentIntent = PendingIntent.getActivity(context, REQUEST_CODE,intent, PendingIntent.FLAG_UPDATE_CURRENT)
            return notification.setContentIntent(contentIntent)
        }

        private companion object {
            private const val REQUEST_CODE = 321
        }

    }
}