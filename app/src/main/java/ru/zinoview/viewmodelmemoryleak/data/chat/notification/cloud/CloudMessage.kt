package ru.zinoview.viewmodelmemoryleak.data.chat.notification.cloud

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.ui.core.MainActivity

interface CloudMessage {

    fun notification(context: Context) : Notification

    class Base(
        // todo
        val id: String,
        private val senderNickName: String,
        private val content: String
    ) : CloudMessage {

        override fun notification(context: Context) : Notification {

            val intent = Intent(context,MainActivity::class.java).apply {
                putExtra("fragment_key","chat")
            }
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            val pIntent = PendingIntent.getActivity(context, 100,intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val notification = NotificationCompat.Builder(context, "Push messages")
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(senderNickName)
                .setGroup("Push messages")
                .setContentIntent(pIntent)
                .build()

            return notification
        }
    }
}