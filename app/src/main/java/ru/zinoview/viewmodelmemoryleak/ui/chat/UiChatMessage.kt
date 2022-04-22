package ru.zinoview.viewmodelmemoryleak.ui.chat

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper
import ru.zinoview.viewmodelmemoryleak.core.chat.Message
import ru.zinoview.viewmodelmemoryleak.ui.chat.edit.EditMessageListener
import ru.zinoview.viewmodelmemoryleak.ui.chat.state.UiState
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.ViewWrapper
import ru.zinoview.viewmodelmemoryleak.ui.core.*

interface UiChatMessage :
    DiffSame<UiChatMessage>, UiSame, Bind, Ui, ChangeTitle<ToolbarActivity>,
    Message, Show<ViewWrapper> {

    override fun isContentTheSame(item: UiChatMessage) = false
    override fun isItemTheSame(item: UiChatMessage) = false

    override fun same(data: String,isRead: Boolean) = false
    override fun sameId(id: String) = false

    override fun bind(view: TextView) = Unit
    override fun bind(view: TextView, stateImage: ImageView,editImage: ImageView) = Unit
    override fun changeTitle(toolbar: ToolbarActivity) = Unit

    override fun <T> map(mapper: Mapper<T>): T = mapper.map()
    fun edit(listener: EditMessageListener) = Unit

    override fun show(arg: ViewWrapper) = Unit

    fun addScroll(scrollCommunication: ScrollCommunication) = Unit

    //todo remove
    fun mapToOldMessage() : UiChatMessage = Empty
    fun messageSessionState(): UiState.MessageSession = UiState.MessageSession()

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
        private val content: String,
        private val isRead: Boolean
    ) : UiChatMessage {

        override fun bind(view: TextView) {
            view.text = content
        }

        override fun bind(view: TextView, stateImage: ImageView,editImage: ImageView) {
            bind(view)
            stateImage.visibility = View.GONE
        }

        override fun isItemTheSame(item: UiChatMessage) = item.sameId(id)
        override fun isContentTheSame(item: UiChatMessage) = item.same(content,isRead)

        override fun same(data: String,isRead: Boolean)
            = content == data && this.isRead == isRead

        override fun sameId(id: String) = this.id == id

        override fun changeTitle(toolbar: ToolbarActivity)
            = toolbar.changeTitle(TITLE)

        override fun addScroll(scrollCommunication: ScrollCommunication) {
            scrollCommunication.postValue(UiScroll.Base())
        }

        private companion object {
            private const val TITLE = "Chat"
        }
    }

    abstract class Sent(
        private val id: String,
        private val content: String,
        private val senderId: String,
        private val senderNickname: String,
        private val isRead: Boolean
    ) : Abstract(id,content,isRead) {

        override fun bind(view: TextView, stateImage: ImageView, editImage: ImageView) {
            super.bind(view, stateImage, editImage)
            editImage.visibility = View.VISIBLE
        }

        override fun edit(listener: EditMessageListener) = listener.edit(this)
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(id)

        override fun show(view: ViewWrapper) {
            view.show(Unit,content)
        }

        override fun mapToOldMessage() = OldMessage(id, content)

        data class Read(
            private val id: String,
            private val content: String,
            private val senderId: String,
            private val senderNickname: String
        ) : Sent(id, content, senderId, senderNickname,true)


        data class Unread(
            private val id: String,
            private val content: String,
            private val senderId: String,
            private val senderNickname: String
        ) : Sent(id,content, senderId, senderNickname,false)
    }

    data class Received(
        private val id: String,
        private val content: String,
        private val senderId: String,
        private val senderNickname: String
    ) : Abstract(id,content,false)

    class ProgressMessage(
        private val senderId: String,
        private val content: String,
    ) : Abstract(senderId,content,false) {

        override fun bind(view: TextView, stateImage: ImageView,editImage: ImageView) {
            super.bind(view)
            stateImage.visibility = View.VISIBLE
        }

        override fun addScroll(scrollCommunication: ScrollCommunication) = Unit

        override fun changeTitle(toolbar: ToolbarActivity) = Unit
    }

    class OldMessage(
        private val id: String,
        private val content: String
    ) :UiChatMessage {

        override fun show(view: ViewWrapper) = view.show(Unit,content)
        override fun messageSessionState()
            = UiState.MessageSession(content,id)
    }
}