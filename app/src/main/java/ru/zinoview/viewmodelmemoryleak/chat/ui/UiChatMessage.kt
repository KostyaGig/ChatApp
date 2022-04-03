package ru.zinoview.viewmodelmemoryleak.chat.ui

import android.util.Log
import android.widget.TextView
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Bind
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.DiffSame
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Same

interface UiChatMessage : DiffSame<UiChatMessage>, Same, Bind {

    override fun isContentTheSame(item: UiChatMessage) = false
    override fun isItemTheSame(item: UiChatMessage) = false

    override fun bind(view: TextView) = Unit

    abstract class Message(
        private val id: Int,
        private val content: String
    ) : UiChatMessage {

        override fun bind(view: TextView) {
            view.text = content
        }

        override fun isItemTheSame(item: UiChatMessage) : Boolean {
            val result = item.sameId(id)
            Log.d("zinoviewk","isItemTheSame $result")
            return result
        }
        override fun isContentTheSame(item: UiChatMessage) : Boolean{
            val result = item.same(content)
            Log.d("zinoviewk","isContentTheSame $result")
            return result
        }

        override fun same(data: String) = content == data
        override fun sameId(id: Int) = this.id == id
    }

    data class Sent(
        private val id: Int,
        private val content: String,
        private val senderId: Int,
        private val receiverId: Int
    ) : Message(id,"senderId: $senderId $content")

    data class Received(
        private val id: Int,
        private val content: String,
        private val senderId: Int,
        private val receiverId: Int
    ) : Message(id,"senderId: $senderId $content")
}