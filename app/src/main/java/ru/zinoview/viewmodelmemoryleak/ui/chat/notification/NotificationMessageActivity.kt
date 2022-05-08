package ru.zinoview.viewmodelmemoryleak.ui.chat.notification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.zinoview.viewmodelmemoryleak.R

class NotificationMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_message)


        val messageNotificationId = intent?.getStringExtra("message_id") ?: ""
        if (savedInstanceState == null) {
            Log.d("zinoviewk","fragment is chat -, $messageNotificationId")
//            supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.fragment_container2,JoinUserFragment())
//                .commitNow()
        } else {
            Log.d("zinoviewk","BUNDLE !!NULL fragment is chat , $messageNotificationId")
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        Log.d("zinoviewk","ONNEWINTENT")
    }
}