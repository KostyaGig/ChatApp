package ru.zinoview.viewmodelmemoryleak.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.zinoview.viewmodelmemoryleak.core.Mapper
import ru.zinoview.viewmodelmemoryleak.core.chat.ReadMessages
import ru.zinoview.viewmodelmemoryleak.ui.chat.ChatViewModel
import ru.zinoview.viewmodelmemoryleak.ui.chat.UiMessage
import ru.zinoview.viewmodelmemoryleak.ui.chat.edit.MessageSession
import ru.zinoview.viewmodelmemoryleak.ui.core.Action
import ru.zinoview.viewmodelmemoryleak.ui.core.ChangeTitle
import ru.zinoview.viewmodelmemoryleak.ui.core.ToolbarActivity

interface BundleUser : Action<ChatViewModel>, ChangeTitle<ToolbarActivity>, Parcelable,
    Mapper<String, UiMessage.EditedMessage> {

    fun sendMessage(
        viewModel: ChatViewModel,
        messageSession: MessageSession,
        content: String
    ) = Unit

    override fun doAction(arg: ChatViewModel) = Unit
    override fun changeTitle(arg: ToolbarActivity) = Unit
    override fun map(src: String): UiMessage.EditedMessage = UiMessage.EditedMessage.Empty

    fun readMessages(range: Pair<Int, Int>, viewModel: ChatViewModel) = Unit

    @Parcelize
    data class Base(
        private val receiverId: String,
        private val nickName: String
    ) : BundleUser {

        override fun doAction(viewModel: ChatViewModel) = viewModel.messages(receiverId)
        override fun changeTitle(toolbar: ToolbarActivity) = toolbar.changeTitle(nickName)

        override fun sendMessage(
            viewModel: ChatViewModel,
            messageSession: MessageSession,
            content: String
        ) = messageSession.sendMessage(viewModel, receiverId, content)

        override fun map(messageId: String): UiMessage.EditedMessage.Base {
            return UiMessage.EditedMessage.Base(
                messageId, receiverId
            )
        }

        override fun readMessages(range: Pair<Int, Int>, viewModel: ChatViewModel) =
            viewModel.readMessages(
                ReadMessages.Base(range, receiverId)
            )
    }

    @Parcelize
    object Empty : BundleUser
}