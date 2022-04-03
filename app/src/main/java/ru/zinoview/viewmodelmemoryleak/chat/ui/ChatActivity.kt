package ru.zinoview.viewmodelmemoryleak.chat.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.chat.ChatAdapter
import kotlin.concurrent.thread

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val chatRv = findViewById<RecyclerView>(R.id.chat_rv)
        val callback = ChatMessageDiffUtil()
        val sizeListener = ItemSizeListener.Base(chatRv)
        val adapter = ChatAdapter(callback,sizeListener)

        chatRv.adapter = adapter

        val messages = UiChatMessagesObservable()
        val messageObserver = UiChatMessagesObserver(adapter)

        messages.observe(messageObserver)

        thread {
            for (i in 0..100) {
                Thread.sleep(1000)
                if (i % 2 == 0) {
                    runOnUiThread {
                        messages.add(
                            listOf(
                                UiChatMessage.Sent(
                                    i,"content $i",1,2
                                )
                            )
                        )
                    }

                } else {
                    runOnUiThread {
                        messages.add(
                            listOf(
                                UiChatMessage.Received(
                                    i,"content $i",2,1
                                )
                            )
                        )
                    }

                }

            }
        }

//        thread {
//            runOnUiThread {
//                adapter.submitList(listOf(
//                    UiChatMessage.Received(
//                        1,"content $1",2,1
//                    )
//                ))
//            }
//
//
//            Thread.sleep(1000)
//            runOnUiThread {
//                adapter.submitList(listOf(
//                    UiChatMessage.Received(
//                        1,"content $1",2,1
//                    ),
//                    UiChatMessage.Received(
//                        2,"content $2",2,1
//                    )
//                ))
//            }
//
//
//            Thread.sleep(1000)
//
//            runOnUiThread {
//                adapter.submitList(listOf(
//                    UiChatMessage.Received(
//                        1,"content $1",2,1
//                    ),
//                    UiChatMessage.Sent(
//                        1,"content $2",2,1
//                    ),
//                    UiChatMessage.Received(
//                        3,"content $3",2,1
//                    )
//                ))
//            }
//
//        }


    }



}