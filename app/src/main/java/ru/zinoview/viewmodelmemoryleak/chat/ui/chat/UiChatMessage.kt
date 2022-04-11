package ru.zinoview.viewmodelmemoryleak.chat.ui.chat

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import ru.zinoview.viewmodelmemoryleak.chat.core.chat.Mapper
import ru.zinoview.viewmodelmemoryleak.chat.core.chat.Message
import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.edit.EditMessageListener
import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.view.ViewWrapper
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.*

interface UiChatMessage :
    DiffSame<UiChatMessage>, Same, Bind, Ui,ChangeTitle<ToolbarActivity>, Message, Show<ViewWrapper> {

    override fun isContentTheSame(item: UiChatMessage) = false
    override fun isItemTheSame(item: UiChatMessage) = false

    override fun same(data: String) = false
    override fun sameId(id: String) = false

    override fun bind(view: TextView) = Unit
    override fun bind(view: TextView, stateImage: ImageView,editImage: ImageView) = Unit
    override fun changeTitle(toolbar: ToolbarActivity) = Unit

    override fun <T> map(mapper: Mapper<T>): T = mapper.map()
    fun edit(listener: EditMessageListener) = Unit

    override fun show(arg: ViewWrapper) = Unit

    object Empty : UiChatMessage

    class Failure(
        private val message: String
    ) : UiChatMessage {

        override fun changeTitle(toolbar: ToolbarActivity)
            = toolbar.changeTitle(message)
    }

    object Progress : UiChatMessage {

        override fun changeTitle(toolbar: ToolbarActivity) {
            toolbar.changeTitle(TITLE)
        }

        private const val TITLE = "Progress..."
    }

    abstract class Abstract(
        private val id: String,
        private val content: String
    ) : UiChatMessage {

        override fun bind(view: TextView) {
            view.text = content
        }

        override fun bind(view: TextView, stateImage: ImageView,editImage: ImageView) {
            bind(view)
            stateImage.visibility = View.GONE
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
    ) : Abstract(id,content) {

        override fun bind(view: TextView, stateImage: ImageView, editImage: ImageView) {
            super.bind(view, stateImage, editImage)
            editImage.visibility = View.VISIBLE
        }

        override fun edit(listener: EditMessageListener) = listener.edit(this)
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(id)

        override fun show(view: ViewWrapper) {
            view.show(Unit,content)
        }
    }

    data class Received(
        private val id: String,
        private val content: String,
        private val senderId: String,
        private val senderNickname: String
    ) : Abstract(id,content)

    class ProgressMessage(
        private val senderId: String,
        private val content: String,
    ) : Abstract(senderId,content) {

        override fun bind(view: TextView, stateImage: ImageView,editImage: ImageView) {
            super.bind(view)
            stateImage.visibility = View.VISIBLE
        }

        override fun changeTitle(toolbar: ToolbarActivity) = Unit
    }

}