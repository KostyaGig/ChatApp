package ru.zinoview.viewmodelmemoryleak.ui.chat.notification

import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

interface GroupId : CreateChannel {

    fun sendMessageNotification(iconId: Int,content: String,time: String) : NotificationWrapper = NotificationWrapper.Empty

    fun editMessageNotification(iconId: Int,content: String,time: String) : NotificationWrapper = NotificationWrapper.Empty

    abstract class Abstract : GroupId {

        protected companion object {
            const val SM_GROUP_ID = "SM_GROUP"
            const val EM_GROUP_ID = "EM_GROUP"
        }
    }

    class Notification(private val channel: Channel) : Abstract() {

        override fun sendMessageNotification(iconId: Int, content: String,time: String)
            = channel.notification(iconId,content,SM_GROUP_ID,time)

        override fun editMessageNotification(iconId: Int, content: String,time: String)
            = channel.notification(iconId,content, EM_GROUP_ID,time)

        override fun createChannel()  = Unit
    }

    // todo move create channels to application class after start the application
    class Base(
        private val context: Context
    ) : Abstract() {

        @RequiresApi(Build.VERSION_CODES.O)
        override fun createChannel() {
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channels = listOf(
                NotificationChannel(SM_GROUP_ID, SM_GROUP_ID, NotificationManager.IMPORTANCE_DEFAULT),
                NotificationChannel(EM_GROUP_ID, EM_GROUP_ID, NotificationManager.IMPORTANCE_DEFAULT),
            )

            manager.createNotificationChannelGroups(
                listOf(
                    NotificationChannelGroup(SM_GROUP_ID, SM_GROUP_ID),
                    NotificationChannelGroup(EM_GROUP_ID, EM_GROUP_ID),
                )
            )

            channels.forEach { channel ->
                manager.createNotificationChannel(channel)
            }
        }
    }
}