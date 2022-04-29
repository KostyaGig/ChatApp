package ru.zinoview.viewmodelmemoryleak.data.chat.user_status

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface FirebaseMessageNotification {

    suspend fun notificationToken() : String

    class Base : FirebaseMessageNotification {

        override suspend fun notificationToken() = suspendCoroutine<String> { continuation ->
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    continuation.resume(token)
                } else {
                    Log.d("zinoviewk","FirebaseMessageNotification -> notificationToken() error")
                }
            }
        }
    }
}