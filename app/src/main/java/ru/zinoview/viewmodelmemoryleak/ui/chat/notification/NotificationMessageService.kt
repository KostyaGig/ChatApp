package ru.zinoview.viewmodelmemoryleak.ui.chat.notification

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class NotificationMessageService : Service() {
    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("zinoviewk","start service ")
        val messageNotificationId = intent?.getStringExtra("message_id") ?: ""
        if (messageNotificationId.isNotEmpty()) {
            Log.d("zinoviewk","start service $messageNotificationId")
//            startActivity(
//                Intent(this,NotificationMessageActivity::class.java).apply {
//                    putExtra("message_id",messageNotificationId)
//                }
//            )
        }

        return START_NOT_STICKY
    }

}