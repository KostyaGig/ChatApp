package ru.zinoview.viewmodelmemoryleak.ui.connection

import ru.zinoview.viewmodelmemoryleak.ui.chat.ChatViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.*

interface UiConnection : ChangeTitle<ToolbarActivity>, SameOne<UiConnection> {

    fun messages(viewModel: ChatViewModel) = Unit

    override fun changeTitle(arg: ToolbarActivity) = Unit
    override fun same(data: UiConnection) = false

    data class Success(
        private val message: String
    ) : UiConnection {

        override fun changeTitle(toolbar: ToolbarActivity)
            = toolbar.changeTitle(message)

        override fun messages(viewModel: ChatViewModel)
            = viewModel.messages()

        override fun same(data: UiConnection) = data is Success
    }

    data class Message(private val message: String) : UiConnection {

        override fun changeTitle(toolbar: ToolbarActivity)
            = toolbar.changeTitle(message)

        override fun same(data: UiConnection)
            = false
    }

    object Empty : UiConnection
}