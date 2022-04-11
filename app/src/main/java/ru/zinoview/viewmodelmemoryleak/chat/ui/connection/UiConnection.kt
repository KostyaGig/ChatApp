package ru.zinoview.viewmodelmemoryleak.chat.ui.connection

import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.ChatAdapter
import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.ChatViewModel
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.ChangeTitle
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.ToolbarActivity

interface UiConnection : ChangeTitle<ToolbarActivity> {

    fun showError(adapter: ChatAdapter) = Unit

    fun messages(viewModel: ChatViewModel) = Unit

    class Success(
        private val message: String
    ) : UiConnection {

        override fun changeTitle(toolbar: ToolbarActivity) = toolbar.changeTitle(message)

        override fun messages(viewModel: ChatViewModel)
            = viewModel.messages()
    }

    class Failure(private val message: String) : UiConnection {

        override fun changeTitle(toolbar: ToolbarActivity)
            = toolbar.changeTitle(message)

    }
}