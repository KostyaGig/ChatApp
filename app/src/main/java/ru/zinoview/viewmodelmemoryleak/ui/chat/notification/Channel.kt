package ru.zinoview.viewmodelmemoryleak.ui.chat.notification

import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

interface Channel : CreateChannel {

    fun sendMessageNotification(iconId: Int,content: String,time: String) : NotificationWrapper = NotificationWrapper.Empty

    fun editMessageNotification(iconId: Int,content: String,time: String) : NotificationWrapper = NotificationWrapper.Empty

    abstract class Abstract : Channel {

        protected companion object {
            const val SM_GROUP_ID = "SM_GROUP"
            const val EM_GROUP_ID = "EM_GROUP"
            const val PM = "Push messages"
        }
    }

    class Processing(
            private val uniqueNotification: UniqueNotification
            ) : Abstract() {

        override fun sendMessageNotification(iconId: Int, content: String,time: String)
            = uniqueNotification.notification(iconId,content,SM_GROUP_ID,time)

        override fun editMessageNotification(iconId: Int, content: String,time: String)
            = uniqueNotification.notification(iconId,content, EM_GROUP_ID,time)

        override fun createChannel()  = Unit
    }

    class Base(
        private val context: Context
    ) : Abstract() {

        override fun createChannel() {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannelGroups(
                    listOf(
                        NotificationChannelGroup(SM_GROUP_ID, SM_GROUP_ID),
                        NotificationChannelGroup(EM_GROUP_ID, EM_GROUP_ID),
                    )
                )

                val channels = listOf(
                    NotificationChannel(SM_GROUP_ID, SM_GROUP_ID, NotificationManager.IMPORTANCE_DEFAULT),
                    NotificationChannel(EM_GROUP_ID, EM_GROUP_ID, NotificationManager.IMPORTANCE_DEFAULT),
                    NotificationChannel(PM, PM, NotificationManager.IMPORTANCE_DEFAULT),
                )

                channels.forEach { channel ->
                    manager.createNotificationChannel(channel)
                }
            }
        }
    }
}