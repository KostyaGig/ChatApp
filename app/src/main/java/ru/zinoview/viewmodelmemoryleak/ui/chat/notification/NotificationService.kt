package ru.zinoview.viewmodelmemoryleak.ui.chat.notification

import android.app.Notification
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationManagerCompat

interface NotificationService {

    fun show(id: Int,notification: Notification,time: String)
    fun disconnect(id: Int,time: String)

    class Base(
        private val context: Context
    ) : NotificationService {

        private val notificationTimes = ArrayList<String>()

        override fun show(id: Int, notification: Notification,time: String) {
            if (notificationTimes.contains(time).not()) {
                NotificationManagerCompat.from(context).notify(id,notification)
                notificationTimes.add(time)
            }
        }

        override fun disconnect(id: Int,time: String) {
            NotificationManagerCompat.from(context).cancel(id)
            notificationTimes.remove(time)
        }

    }
}