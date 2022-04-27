package ru.zinoview.viewmodelmemoryleak.ui.chat.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider

interface Channel : CreateChannel {
    fun notification(iconRes: Int, content: String) : NotificationWrapper

    class Base(
        private val context: Context,
        private val resourceProvider: ResourceProvider,
        private val notificationId: NotificationId
    ) : Channel {

        @RequiresApi(Build.VERSION_CODES.O)
        override fun createChannel() {
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(ID, NAME, NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        override fun notification(iconRes: Int, content: String) : NotificationWrapper {
            val notification = NotificationCompat.Builder(context, ID)
                .setContentTitle(resourceProvider.string(R.string.waiting_for_network))
                .setContentText(content)
                .setSmallIcon(iconRes)
                .setChannelId(ID)
                .build()

            return NotificationWrapper.Base(notificationId.id(), notification)
        }

        private companion object {
            private const val ID = "messages"
            private const val NAME = "chat app"
        }
    }
}