package ru.zinoview.viewmodelmemoryleak.ui.chat.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

interface PushNotificationService {

    class Base : PushNotificationService, FirebaseMessagingService() {

        override fun onNewToken(token: String) {
            super.onNewToken(token)
            Log.d("zinoviewk","Firebase onNewToken $token")
        }

        override fun onMessageReceived(message: RemoteMessage) {
            super.onMessageReceived(message)
            Log.d("zinoviewk","onMessageReceived")
        }
    }
}