package ru.zinoview.viewmodelmemoryleak.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.zinoview.viewmodelmemoryleak.ui.chat.ChatViewModel
import ru.zinoview.viewmodelmemoryleak.ui.chat.edit.MessageSession
import ru.zinoview.viewmodelmemoryleak.ui.core.Action
import ru.zinoview.viewmodelmemoryleak.ui.core.ChangeTitle
import ru.zinoview.viewmodelmemoryleak.ui.core.ToolbarActivity

interface BundleUser : Action<ChatViewModel>,ChangeTitle<ToolbarActivity>,Parcelable {
    fun sendMessage(
        viewModel: ChatViewModel,
        messageSession: MessageSession,
        content: String
    ) = Unit

    override fun doAction(arg: ChatViewModel) = Unit
    override fun changeTitle(arg: ToolbarActivity) = Unit

    @Parcelize
    data class Base(
        private val id: String,
        private val nickName: String
    ) : BundleUser {

        override fun doAction(viewModel: ChatViewModel) = viewModel.messages(id)
        override fun changeTitle(toolbar: ToolbarActivity) = toolbar.changeTitle(nickName)

        override fun sendMessage(
            viewModel: ChatViewModel,
            messageSession: MessageSession,
            content: String
        ) = messageSession.sendMessage(viewModel,id,content)
    }

    @Parcelize
    object Empty : BundleUser
}