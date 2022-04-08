package ru.zinoview.viewmodelmemoryleak.chat.ui.chat

import android.widget.TextView
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Bind
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.DiffSame
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Same
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Ui

interface UiChatMessage : DiffSame<UiChatMessage>, Same, Bind, Ui {

    override fun isContentTheSame(item: UiChatMessage) = false
    override fun isItemTheSame(item: UiChatMessage) = false

    override fun same(data: String) = false
    override fun sameId(id: String) = false
    override fun bind(view: TextView) = Unit

    object Empty : UiChatMessage

    abstract class Abstract(
        private val id: String,
        private val content: String
    ) : UiChatMessage {

        override fun bind(view: TextView) {
            view.text = content
        }

        override fun isItemTheSame(item: UiChatMessage) = item.sameId(id)
        override fun isContentTheSame(item: UiChatMessage) = item.same(content)

        override fun same(data: String) = content == data
        override fun sameId(id: String) = this.id == id
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
    ) : Abstract(senderNickname,content)

    data class Received(
        private val id: String,
        private val content: String,
        private val senderId: String,
        private val senderNickname: String
    ) : Abstract(senderNickname,content)
}