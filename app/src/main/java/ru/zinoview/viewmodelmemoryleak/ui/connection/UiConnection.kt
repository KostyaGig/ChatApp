package ru.zinoview.viewmodelmemoryleak.ui.connection

import ru.zinoview.viewmodelmemoryleak.ui.chat.ChatAdapter
import ru.zinoview.viewmodelmemoryleak.ui.chat.ChatViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.ChangeTitle
import ru.zinoview.viewmodelmemoryleak.ui.core.ToolbarActivity

interface UiConnection : ChangeTitle<ToolbarActivity> {

    fun showError(adapter: ChatAdapter) = Unit

    fun messages(viewModel: ChatViewModel) = Unit

    data class Success(
        private val message: String
    ) : UiConnection {

        override fun changeTitle(toolbar: ToolbarActivity) = toolbar.changeTitle(message)

        override fun messages(viewModel: ChatViewModel)
            = viewModel.messages()
    }

    data class Failure(private val message: String) : UiConnection {

        override fun changeTitle(toolbar: ToolbarActivity)
            = toolbar.changeTitle(message)

    }
}