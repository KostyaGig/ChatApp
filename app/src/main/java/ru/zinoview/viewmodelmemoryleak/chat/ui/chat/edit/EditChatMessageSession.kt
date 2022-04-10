package ru.zinoview.viewmodelmemoryleak.chat.ui.chat.edit

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.chat.data.core.cloud.Disconnect
import ru.zinoview.viewmodelmemoryleak.chat.ui.UiEditChatMessage
import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.ChatViewModel
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Show
import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.view.ViewWrapper

interface EditChatMessageSession : Disconnect<Unit>, EditContent, Show<Unit> {

    fun addMessage(message: UiEditChatMessage)

    fun sendMessage(viewModel: ChatViewModel,content: String)

    class Base(
        private val viewWrapper: ViewWrapper,
        private val editTextWrapper: ViewWrapper
    ) : EditChatMessageSession {

        private var message: UiEditChatMessage = UiEditChatMessage.Empty

        override fun addMessage(message: UiEditChatMessage) {
            this.message = message
        }

        override fun editContent(content: String) = message.editContent(content)

        override fun sendMessage(viewModel: ChatViewModel,content: String) {
            editTextWrapper.disconnect(Unit)
            if (message.isNotEmpty()) {
                message.editContent(content)
                message.doAction(viewModel)
            }
            else {
                if (content.isNotEmpty()) {
                    viewModel.doAction(content)
                } else {
//                     TODO SHOW SNACK BAR
                }
            }
            disconnect(Unit)
        }

        override fun show(arg: Unit) = viewWrapper.show(Unit)

        override fun disconnect(arg: Unit) {
            message = UiEditChatMessage.Empty
            viewWrapper.disconnect(Unit)
        }
    }
}