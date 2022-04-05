package ru.zinoview.viewmodelmemoryleak.chat.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import io.socket.client.IO
import io.socket.client.Socket
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.Connect

class ChatActivity : AppCompatActivity() {

    private val viewModel: ChatViewModel by lazy{
        ViewModelProvider(
            this,
            ChatViewModelFactory.Base(
                CloudDataSource.Count(
                    IO.socket("http://10.0.2.2:3000"),
                    Connect.Base()
                )
            )
        )[ChatViewModel.Base::class.java]
    }

    private lateinit var resultTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_fake)

//        val chatRv = findViewById<RecyclerView>(R.id.chat_rv)
//        val callback = ChatMessageDiffUtil()
//        val adapter = ChatAdapter(callback)
//
//        chatRv.adapter = adapter

        resultTv = findViewById(R.id.result_tv)
        val tapBtn = findViewById<Button>(R.id.tap_btn)

        tapBtn.setOnClickListener {
            viewModel.emit()
        }
    }

    override fun onPause() {
        super.onPause()

        viewModel.disconnect()
    }

    override fun onStart() {
        super.onStart()

        viewModel.observe { model ->
            model.show(resultTv)
        }
    }


}