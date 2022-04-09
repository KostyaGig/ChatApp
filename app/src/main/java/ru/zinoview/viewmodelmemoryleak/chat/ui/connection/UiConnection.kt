package ru.zinoview.viewmodelmemoryleak.chat.ui.connection

import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.ChatAdapter
import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.ChatViewModel
import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.UiChatMessage
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.ChangeTitle
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.ToolbarActivity

interface UiConnection : ChangeTitle<ToolbarActivity> {

    fun showError(adapter: ChatAdapter) = Unit

    fun messages(viewModel: ChatViewModel) = Unit

    class Connection(
        private val message: String
    ) : UiConnection {

        override fun changeTitle(toolbar: ToolbarActivity) = toolbar.changeTitle(message)

        override fun messages(viewModel: ChatViewModel)
            = viewModel.messages()
    }

    class Disconnection(
        private val message: String
    ) : UiConnection {

        override fun changeTitle(toolbar: ToolbarActivity)
            = toolbar.changeTitle(message)

        override fun showError(adapter: ChatAdapter) {
            adapter.submitList(listOf(
                UiChatMessage.Failure(message)
            ))
        }
    }

    class ToolbarDisconnection(private val message: String) : UiConnection {

        override fun changeTitle(toolbar: ToolbarActivity)
            = toolbar.changeTitle(message)
    }
}