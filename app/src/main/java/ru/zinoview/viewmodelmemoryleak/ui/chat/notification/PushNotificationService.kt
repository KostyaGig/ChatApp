package ru.zinoview.viewmodelmemoryleak.ui.chat.notification

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.zinoview.viewmodelmemoryleak.data.chat.notification.cloud.CloudMapToCloudMessageMapper
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

            Log.d("zinoviewk","onMessageReceived")

            val notificationService = NotificationService.Push(applicationContext)
            val mapper = CloudMapToCloudMessageMapper.Base(EmptyString.Base())

            val cloudMessage = mapper.map(message.data)

            val notification = cloudMessage.notification(applicationContext)
            notification.show(notificationService)
        }
    }
}