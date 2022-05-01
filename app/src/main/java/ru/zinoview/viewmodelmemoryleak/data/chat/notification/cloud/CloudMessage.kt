package ru.zinoview.viewmodelmemoryleak.data.chat.notification.cloud

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import ru.zinoview.viewmodelmemoryleak.R

interface CloudMessage {

    fun notification(context: Context) : Notification

    class Base(
        // todo
        val id: String,
        private val senderNickName: String,
        private val content: String
    ) : CloudMessage {

        override fun notification(context: Context) : Notification {
            val notification = NotificationCompat.Builder(context, "Push messages")
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(senderNickName)
                .setGroup("Push messages")
                .build()

            return notification
        }
    }
}