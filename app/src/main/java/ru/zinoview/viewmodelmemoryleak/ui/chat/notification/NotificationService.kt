package ru.zinoview.viewmodelmemoryleak.ui.chat.notification

import android.app.Notification
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationManagerCompat

interface NotificationService {

    fun show(notification: Notification,time: String,id: Int = -1)
    fun disconnect(data: String,id: Int = -1)

    class Base(
        private val context: Context
    ) : NotificationService {

        private val notificationTimes = ArrayList<String>()

        override fun show(notification: Notification,time: String,id: Int) {
            if (notificationTimes.contains(time).not()) {
                NotificationManagerCompat.from(context).notify(id,notification)
                notificationTimes.add(time)
            }
        }

        override fun disconnect(time: String,id: Int) {
            NotificationManagerCompat.from(context).cancel(id)
            notificationTimes.remove(time)
        }

    }

    class Push(
        private val context: Context
    ) : NotificationService {

        override fun show(notification: Notification,tag: String, id: Int)
            = NotificationManagerCompat.from(context).notify(tag,PUSH_MESSAGE_ID,notification)

        override fun disconnect( tag: String,id: Int)
            = NotificationManagerCompat.from(context).cancel(tag,PUSH_MESSAGE_ID)

        private companion object {
            private const val PUSH_MESSAGE_ID = 1
        }
    }
}