package ru.zinoview.viewmodelmemoryleak.ui.chat

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import ru.zinoview.viewmodelmemoryleak.core.IsNotEmpty
import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper
import ru.zinoview.viewmodelmemoryleak.core.chat.Message
import ru.zinoview.viewmodelmemoryleak.ui.BundleUser
import ru.zinoview.viewmodelmemoryleak.ui.chat.edit.EditContent
import ru.zinoview.viewmodelmemoryleak.ui.chat.edit.EditMessageListener
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.SaveState
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.UiState
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.UiStateViewModel
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.UiStates
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.ViewWrapper
import ru.zinoview.viewmodelmemoryleak.ui.core.*

interface UiMessage :
    DiffSame<UiMessage>, UiSame, MessageBind, Ui, ChangeTitle<Pair<ToolbarActivity,BundleUser>>,
    Message, Show<ViewWrapper>, UiItem, OnClick<EditMessageListener> {

    override fun isContentTheSame(item: UiMessage) = false
    override fun isItemTheSame(item: UiMessage) = false

    override fun same(data: String,isRead: Boolean) = false
    override fun same(id: String) = false
    override fun sameFound(isFounded: Boolean) = false

    override fun bind(view: TextView) = Unit
    override fun bindNickName(view: TextView) = Unit

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
        isRead: Boolean
    ) : Base(id,content,isRead,false,senderNickname,senderId) {

        override fun bind(view: TextView, stateImage: ImageView, editImage: ImageView) {
            super.bind(view, stateImage, editImage)
            editImage.visibility = View.VISIBLE
        }

        override fun onClick(listener: EditMessageListener) = listener.onClick(this)

        override fun <T> map(mapper: Mapper<T>)
            = mapper.map(id,senderId,content, senderNickname)

        override fun show(view: ViewWrapper) = view.show(Unit,content)


        data class Read(
            private val id: String,
            private val content: String,
            private val senderId: String,
            private val senderNickname: String
        ) : Sent(id, content, senderId, senderNickname,true)


        class Unread(
            private val id: String,
            private val content: String,
            private val senderId: String,
            private val senderNickname: String
        ) : Sent(id,content, senderId, senderNickname,false)

    }

    abstract class Received(
        private val id: String,
        private val content: String,
        private val senderId: String,
        private val senderNickname: String,
        private val isFounded: Boolean
    ) : Base(id,content,false,isFounded,senderNickname,senderId) {

        data class Base(
            private val id: String,
            private val content: String,
            private val senderId: String,
            private val senderNickname: String
        ) : Received(id, content, senderId, senderNickname,false) {

            override fun <T> map(mapper: Mapper<T>) = mapper.map(id,senderId,content, senderNickname)

        }

        data class Found(
            private val id: String,
            private val content: String,
            private val senderId: String,
            private val senderNickname: String
        ) : Received(id, content, senderId, senderNickname,true)

    }


    class ProgressMessage(
        private val senderId: String,
        private val content: String,
        private val senderNickname: String
    ) : Base(senderId,content,false) {

        override fun bind(view: TextView, stateImage: ImageView,editImage: ImageView) {
            super.bind(view)
            stateImage.visibility = View.VISIBLE
        }

        override fun bindNickName(view: TextView) { view.text = senderNickname }

        override fun addScroll(scrollCommunication: ScrollCommunication) = Unit
    }

    interface Typing : UiMessage {

        abstract class Base(private val message: String) : Typing {

            override fun changeTitle(pair: Pair<ToolbarActivity, BundleUser>)
                = pair.first.changeTitle(message)

        }

        class Is(
            private val message: String
        ) : Base(message) {

            override fun bind(view: TextView) {
                view.text = message
            }
        }

         class IsNot(
             message: String
         ) : Base(message)

    }

    interface OldMessage : UiMessage, SaveState {

        class Base(
            private val id: String,
            private val content: String
        ) : OldMessage {

            override fun show(view: ViewWrapper) = view.show(Unit,content)

            override fun <T> map(mapper: Mapper<T>)
                = mapper.map(id,content = content)

            override fun saveState(viewModel: UiStateViewModel, editText: UiState.EditText) {
                viewModel.save(UiStates.Base(
                    editText,
                    UiState.MessageSession(content,id)
                ))
            }
        }

        object Empty : OldMessage {
            override fun saveState(viewModel: UiStateViewModel, editText: UiState.EditText) {
                viewModel.save(UiStates.Base(
                    editText,UiState.MessageSession()
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