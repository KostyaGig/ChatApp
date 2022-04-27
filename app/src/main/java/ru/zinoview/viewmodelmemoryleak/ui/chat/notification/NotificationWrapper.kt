package ru.zinoview.viewmodelmemoryleak.ui.chat.notification

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationManagerCompat
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Disconnect
import ru.zinoview.viewmodelmemoryleak.ui.core.Show

interface NotificationWrapper : Show<Context>, Disconnect<Pair<Context,String>> {


    class Base(
        private val id: Int,
        private val notification: Notification
    ) : NotificationWrapper {

        override fun show(context: Context) {
            NotificationManagerCompat.from(context).notify(id,notification)
        }

        override fun disconnect(pair: Pair<Context,String>) {
            val content = pair.second
            val contentText = notification.extras.getCharSequence(CONTENT_TEXT)
            if (content == contentText) {
                NotificationManagerCompat.from(pair.first).cancel(id)
            }
        }

        private companion object {
            private const val CONTENT_TEXT = "android.text"
        }
    }

    object Empty : NotificationWrapper {
        override fun show(context: Context) = Unit
        override fun disconnect(arg: Pair<Context, String>) = Unit
    }
}