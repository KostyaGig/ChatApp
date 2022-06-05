package ru.zinoview.viewmodelmemoryleak.ui.chat

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import ru.zinoview.viewmodelmemoryleak.core.IsNotEmpty
import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper
import ru.zinoview.viewmodelmemoryleak.core.chat.Message
import ru.zinoview.viewmodelmemoryleak.data.chat.DataMessage
import ru.zinoview.viewmodelmemoryleak.ui.users.BundleUser
import ru.zinoview.viewmodelmemoryleak.ui.chat.edit.EditContent
import ru.zinoview.viewmodelmemoryleak.ui.chat.edit.EditMessageListener
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.SaveState
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.ChatUiState
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.ChatUiStates
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.ChatUiStateViewModel
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.ViewWrapper
import ru.zinoview.viewmodelmemoryleak.ui.core.*

interface UiMessage :
    DiffSame<UiMessage>, UiSame, MessageBind, Ui, ChangeTitle<Pair<ToolbarActivity, BundleUser>>,
    Message, Show<ViewWrapper>, UiItem, OnClick<EditMessageListener> {

    override fun isContentTheSame(item: UiMessage) = false
    override fun isItemTheSame(item: UiMessage) = false

    override fun same(data: String,isRead: Boolean) = false
    override fun same(id: String) = false
    override fun sameFound(isFounded: Boolean) = false

    override fun bind(view: TextView) = Unit
    override fun bindNickName(view: TextView) = Unit
    override fun bindEditedText(view: TextView) = Unit

    override fun changeTitle(arg: Pair<ToolbarActivity, BundleUser>) = Unit

    override fun <T> map(mapper: Mapper<T>): T = mapper.map()

    override fun onClick(item: EditMessageListener) = Unit

    override fun show(arg: ViewWrapper) = Unit

    fun addScroll(scrollCommunication: ScrollCommunication) = Unit

    object Empty : UiMessage

    class Failure(
        private val message: String
    ) : UiMessage {

        override fun changeTitle(pair: Pair<ToolbarActivity, BundleUser>)
            = pair.first.changeTitle(message)
    }

    object Progress : UiMessage {

        override fun changeTitle(pair: Pair<ToolbarActivity, BundleUser>)
            = pair.first.changeTitle(TITLE)

        private const val TITLE = "Progress..."
    }

    abstract class Base(
        private val id: String,
        private val content: String,
        private val isRead: Boolean,
        private val isFounded: Boolean = false,
        private val senderNickname: String = "",
        private val senderId: String = "",
        private val isEdited: Boolean
    ) : UiMessage {

        override fun bind(view: TextView) {
            view.text = content
        }

        override fun bindNickName(view: TextView) {
            view.text = senderNickname
        }

        override fun bind(view: TextView, stateImage: ImageView,editImage: ImageView) {
            bind(view)
            stateImage.visibility = View.GONE
        }

        override fun bindEditedText(view: TextView) {
            if (isEdited) view.visibility = View.VISIBLE else view.visibility = View.GONE
        }

        override fun isItemTheSame(item: UiMessage): Boolean {
            return item.same(id) && item.sameFound(isFounded)
        }
        override fun isContentTheSame(item: UiMessage) = item.same(content,isRead)

        override fun same(data: String,isRead: Boolean)
            = content == data && this.isRead == isRead

        override fun same(id: String) = this.id == id

        override fun sameFound(isFounded: Boolean)
            = this.isFounded == isFounded

        override fun changeTitle(pair: Pair<ToolbarActivity, BundleUser>)
            = pair.second.changeTitle(pair.first)

        override fun addScroll(scrollCommunication: ScrollCommunication)
            = scrollCommunication.postValue(UiScroll.Base())
    }

    abstract class Sent(
        private val id: String,
        private val content: String,
        private val senderId: String,
        private val senderNickname: String,
        isRead: Boolean,
        private val isEdited: Boolean
    ) : Base(id,content,isRead,false,senderNickname,senderId,isEdited) {

        override fun bind(view: TextView, stateImage: ImageView, editImage: ImageView) {
            super.bind(view, stateImage, editImage)
            editImage.visibility = View.VISIBLE
        }

        override fun onClick(listener: EditMessageListener) = listener.onClick(this)

        override fun <T> map(mapper: Mapper<T>)
            = mapper.map(id,senderId,content, senderNickname)

        override fun show(view: ViewWrapper) = view.show(Unit,content)


        abstract class Read(
            private val id: String,
            private val senderId: String,
            private val content: String,
            private val senderNickname: String,
            private val isEdited: Boolean
        ) : Sent(id, content, senderId, senderNickname,true, isEdited) {

            data class BaseReadSent(
                private val id: String,
                private val senderId: String,
                private val content: String,
                private val senderNickname: String
            ) : Read(id, senderId, content, senderNickname,false)

            data class EditedReadSent(
                private val id: String,
                private val senderId: String,
                private val content: String,
                private val senderNickname: String
            ) : Read(id, senderId, content, senderNickname,true)


        }

        abstract class Unread(
            private val id: String,
            private val senderId: String,
            private val content: String,
            private val senderNickname: String,
            private val isEdited: Boolean
        ) : Sent(id,content, senderId, senderNickname,false,isEdited) {

            class Base(
                private val id: String,
                private val senderId: String,
                private val content: String,
                private val senderNickname: String
            ) : Unread(id, senderId, content, senderNickname,false)

            class Edited(
                private val id: String,
                private val senderId: String,
                private val content: String,
                private val senderNickname: String
            ) : Unread(id, senderId, content, senderNickname,true)
        }

    }

    abstract class Received(
        private val id: String,
        private val senderId: String,
        private val content: String,
        private val senderNickname: String,
        private val isFounded: Boolean,
        private val isEdited: Boolean
    ) : Base(id,content,false,isFounded,senderNickname,senderId,isEdited) {

        override fun <T> map(mapper: Mapper<T>) = mapper.map(id,senderId,content, senderNickname)

        data class BaseReceived(
            private val id: String,
            private val senderId: String,
            private val content: String,
            private val senderNickname: String
        ) : Received(id, senderId, content, senderNickname,false,false)

        data class EditedReceived(
            private val id: String,
            private val senderId: String,
            private val content: String,
            private val senderNickname: String
        ) : Received(id, senderId, content, senderNickname,false,true) {


        }


        data class Found(
            private val id: String,
            private val content: String,
            private val senderId: String,
            private val senderNickname: String
        ) : Received(id, content, senderId, senderNickname,true,false)

    }


    class ProgressMessage(
        private val senderId: String,
        private val content: String,
        private val senderNickname: String
    ) : Base(senderId,content,false,false,senderNickname,senderId,false) {

        override fun bind(view: TextView, stateImage: ImageView,editImage: ImageView) {
            super.bind(view)
            stateImage.visibility = View.VISIBLE
        }

        override fun bindNickName(view: TextView) { view.text = senderNickname }

        override fun addScroll(scrollCommunication: ScrollCommunication) = Unit
    }

    interface Typing : UiMessage {

        abstract class Base(private val message: String,private val showMessage: Boolean = true) : Typing {

            override fun changeTitle(pair: Pair<ToolbarActivity, BundleUser>) {
                if (showMessage)
                    pair.first.changeTitle(message)
                else
                    pair.second.changeTitle(pair.first)
            }

        }

        class Is(
            private val message: String
        ) : Base(message) {

            override fun bind(view: TextView) {
                view.text = message
            }
        }

         object IsNot : Base("",false)

    }

    interface OldMessage : UiMessage, SaveState {

        class Base(
            private val id: String,
            private val content: String
        ) : OldMessage {

            override fun show(view: ViewWrapper) = view.show(Unit,content)

            override fun <T> map(mapper: Mapper<T>)
                = mapper.map(id,content = content)

            override fun saveState(viewModel: ChatUiStateViewModel, editText: ChatUiState.EditText,bundleUser: BundleUser) {
                viewModel.save(ChatUiStates.Base(
                    editText,
                    ChatUiState.MessageSession(content,id),
                ))
            }
        }

        object Empty : OldMessage {
            override fun saveState(viewModel: ChatUiStateViewModel, editText: ChatUiState.EditText,bundleUser: BundleUser) {
                viewModel.save(ChatUiStates.Base(
                    editText,ChatUiState.MessageSession()
                ))
            }
        }
    }



    interface EditedMessage : UiMessage, EditContent, Action<ChatViewModel>, IsNotEmpty<Unit> {

        class Base(
            private val id: String,
            private val receiverId: String
        ) : EditedMessage {
            private var content = ""

            override fun editContent(content: String) {
                this.content = content
            }

            override fun doAction(viewModel: ChatViewModel)
                = viewModel.editMessage(id, content,receiverId)

            override fun isNotEmpty(arg: Unit) = true
        }

        object Empty : EditedMessage {
            override fun editContent(content: String) = Unit

            override fun doAction(arg: ChatViewModel) = Unit
            override fun isNotEmpty(arg: Unit) = false
        }
    }


}