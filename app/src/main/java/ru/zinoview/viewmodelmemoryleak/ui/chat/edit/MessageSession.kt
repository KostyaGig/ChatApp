package ru.zinoview.viewmodelmemoryleak.ui.chat.edit

import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Disconnect
import ru.zinoview.viewmodelmemoryleak.ui.chat.UiEditChatMessage
import ru.zinoview.viewmodelmemoryleak.ui.chat.ChatViewModel
import ru.zinoview.viewmodelmemoryleak.ui.chat.UiChatMessage
import ru.zinoview.viewmodelmemoryleak.ui.chat.state.ToUiStateMessageSessionMapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.state.UiState
import ru.zinoview.viewmodelmemoryleak.ui.chat.state.UiStateMessageSession
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.SnackBar
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.ViewWrapper
import ru.zinoview.viewmodelmemoryleak.ui.core.Show

interface MessageSession : Disconnect<Unit>, EditContent, Show<Unit> {

    fun addMessage(message: UiEditChatMessage) = Unit

    // todo rewrite
    fun addOldMessage(message: UiChatMessage) = Unit
    fun messageSessionState(): UiState.MessageSession = UiState.MessageSession()

    fun sendMessage(viewModel: ChatViewModel, content: String) = Unit

    override fun show(arg: Unit) = Unit
    override fun editContent(content: String) = Unit
    override fun disconnect(arg: Unit) = Unit

    class Base(
        private val viewWrapper: ViewWrapper,
        private val editTextWrapper: ViewWrapper,
        private val snackBar: SnackBar<Unit>
    ) : MessageSession {

        private var message: UiEditChatMessage = UiEditChatMessage.Empty
        private var oldMessage: UiChatMessage = UiChatMessage.Empty

        override fun addMessage(message: UiEditChatMessage) {
            this.message = message
        }

        override fun addOldMessage(message: UiChatMessage) {
            this.oldMessage = message
        }

        override fun messageSessionState()
            = oldMessage.messageSessionState()

        override fun editContent(content: String) = message.editContent(content)

        override fun sendMessage(viewModel: ChatViewModel,content: String) {
            editTextWrapper.disconnect(Unit)
            if (message.isNotEmpty(Unit)) {
                message.editContent(content)
                message.doAction(viewModel)
            }
            else {
                if (content.isNotEmpty()) {
                    viewModel.doAction(content)
                } else {
                    snackBar.show(Unit)
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

    object Empty : MessageSession
}