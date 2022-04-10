package ru.zinoview.viewmodelmemoryleak.chat.ui

import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.ChatViewModel
import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.edit.EditContent
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Action

interface UiEditChatMessage : EditContent, Action<ChatViewModel> {

    fun isNotEmpty() : Boolean

    class Base(
        private val messageId: String,
    ) : UiEditChatMessage {

        private var content = ""

        override fun editContent(content: String) {
            this.content = content
        }

        override fun doAction(viewModel: ChatViewModel) = viewModel.editMessage(messageId, content)
        override fun isNotEmpty() = true
    }

    object Empty : UiEditChatMessage {
        override fun editContent(content: String) = Unit

        override fun doAction(arg: ChatViewModel) = Unit
        override fun isNotEmpty() = false
    }

}