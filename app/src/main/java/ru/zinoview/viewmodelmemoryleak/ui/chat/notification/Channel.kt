package ru.zinoview.viewmodelmemoryleak.ui.chat.notification

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider

interface Channel : CreateChannel {
    fun notification(iconRes: Int, content: String,id: String) : NotificationWrapper

    class Base(
        private val context: Context,
        private val resourceProvider: ResourceProvider,
        private val notificationId: NotificationId,
        private val channelId: GroupId
    ) : Channel {

        @RequiresApi(Build.VERSION_CODES.O)
        override fun createChannel() = channelId.createChannel()

        override fun notification(iconRes: Int, content: String,id: String) : NotificationWrapper {
            val notification = NotificationCompat.Builder(context, ID)
                .setContentTitle(resourceProvider.string(R.string.waiting_for_network))
                .setContentText(content)
                .setSmallIcon(iconRes)
                .setChannelId(id)
                .setGroup(id)
                .build()

            return NotificationWrapper.Base(notificationId.id(), notification)
        }

        private companion object {
            private const val ID = "messages"
        }
    }
}