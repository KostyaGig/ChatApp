package ru.zinoview.viewmodelmemoryleak.ui.chat.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.data.chat.notification.cloud.CloudMapToCloudMessageMapper
import ru.zinoview.viewmodelmemoryleak.data.chat.notification.cloud.CloudMessage
import ru.zinoview.viewmodelmemoryleak.data.core.EmptyString

interface PushNotificationService {

    class Base : PushNotificationService, FirebaseMessagingService() {

        override fun onNewToken(token: String) {
            super.onNewToken(token)
            Log.d("zinoviewk","Firebase onNewToken $token")
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onMessageReceived(message: RemoteMessage) {
            super.onMessageReceived(message)

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel("Push messages","Push messages", NotificationManager.IMPORTANCE_DEFAULT)
                val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(channel)
            }
            val notificationService = NotificationService.Push(applicationContext)

            val mapper = CloudMapToCloudMessageMapper.Base(EmptyString.Base())
            val cloudMessage = mapper.map(message.data)

            val notification = cloudMessage.notification(applicationContext)
            val tag = (cloudMessage as CloudMessage.Base).id

            notificationService.show(notification,tag)
        }
    }
}