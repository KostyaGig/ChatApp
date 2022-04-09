package ru.zinoview.viewmodelmemoryleak.chat.ui.chat

import android.widget.TextView
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.*

interface UiChatMessage : DiffSame<UiChatMessage>, Same, Bind, Ui,ChangeTitle<ToolbarActivity> {

    override fun isContentTheSame(item: UiChatMessage) = false
    override fun isItemTheSame(item: UiChatMessage) = false

    override fun same(data: String) = false
    override fun sameId(id: String) = false
    override fun bind(view: TextView) = Unit

    override fun changeTitle(toolbar: ToolbarActivity) = Unit

    fun bindError(view: TextView) = Unit

    object Empty : UiChatMessage

    class Failure(
        private val message: String
    ) : UiChatMessage {
        override fun changeTitle(toolbar: ToolbarActivity)
            = toolbar.changeTitle(message)

        override fun bindError(view: TextView) {
            view.text = message
        }
    }

    object Progress : UiChatMessage {
        override fun changeTitle(toolbar: ToolbarActivity)
            = toolbar.changeTitle(TITLE)

        private const val TITLE = "Progress"
    }

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

        override fun changeTitle(toolbar: ToolbarActivity)
            = toolbar.changeTitle(TITLE)

        private companion object {
            private const val TITLE = "Chat"
        }
    }

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