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

    abstract class Abstract(
        private val id: String,
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
//            Log.d("zinoviewk","isContentTheSame $result")
            return result
        }

        override fun same(data: String) = content == data
        override fun sameId(id: String) : Boolean {
            val result = this.id == id
            Log.d("zinoviewk","same id src ${this.id}, coming $id")
            return result
        }
    }

    data class Base(
        private val id: String,
        private val content: String,
        private val senderId: String,
        private val senderNickname: String
    ) : Abstract(id,content)

    data class Sent(
        private val id: String,
        private val content: String,
        private val senderId: String,
        private val senderNickname: String
    ) : Abstract(id,"messageId: $id $content")

    data class Received(
        private val id: String,
        private val content: String,
        private val senderId: String,
        private val senderNickname: String
    ) : Abstract(id,"messageId: $id $content")
}