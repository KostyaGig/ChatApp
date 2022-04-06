package ru.zinoview.viewmodelmemoryleak.chat.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import io.socket.client.IO
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.SharedPreferencesReader
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.Connect
import kotlin.math.log

class ChatActivity : AppCompatActivity() {

    private val viewModel: ChatViewModel by lazy {
        ViewModelProvider(
            this,
            ChatViewModelFactory.Base(
                CloudDataSource.Chat.Base(
                    IO.socket("http://10.0.2.2:3000"),
                    Connect.Base(),
                    IdSharedPreferences.Base(
                        SharedPreferencesReader.Base(),
                        applicationContext
                    )
                )
            )
        )[ChatViewModel.Base::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_fake)

//        val chatRv = findViewById<RecyclerView>(R.id.chat_rv)
//        val callback = ChatMessageDiffUtil()
//        val adapter = ChatAdapter(callback)
//
//        chatRv.adapter = adapter

        val nicknameField = findViewById<EditText>(R.id.nickname_field)
        val joinButton = findViewById<Button>(R.id.join_btn)

        joinButton.setOnClickListener {
            val nickname = nicknameField.text.toString().trim()
            viewModel.joinUser(nickname)
        }

    }

    override fun onPause() {
        super.onPause()
        viewModel.disconnect()
    }

    override fun onStart() {
        super.onStart()
    }


}