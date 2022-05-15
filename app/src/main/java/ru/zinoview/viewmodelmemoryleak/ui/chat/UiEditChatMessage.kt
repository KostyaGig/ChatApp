package ru.zinoview.viewmodelmemoryleak.ui.chat

import ru.zinoview.viewmodelmemoryleak.core.IsNotEmpty
import ru.zinoview.viewmodelmemoryleak.ui.chat.edit.EditContent
import ru.zinoview.viewmodelmemoryleak.ui.core.Action

interface UiEditChatMessage : EditContent, Action<ChatViewModel>, IsNotEmpty<Unit> {

    class Base(
        private val messageId: String,
        private val receiverId: String
    ) : UiEditChatMessage {

        private var content = ""

        override fun editContent(content: String) {
            this.content = content
        }

        override fun doAction(viewModel: ChatViewModel)
            = viewModel.editMessage(messageId, content,receiverId)

        override fun isNotEmpty(arg: Unit) = true
    }

    object Empty : UiEditChatMessage {
        override fun editContent(content: String) = Unit

        override fun doAction(arg: ChatViewModel) = Unit
        override fun isNotEmpty(arg: Unit) = false
    }

}