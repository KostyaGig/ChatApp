package ru.zinoview.viewmodelmemoryleak.ui.chat.edit

import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Disconnect
import ru.zinoview.viewmodelmemoryleak.ui.chat.ChatViewModel
import ru.zinoview.viewmodelmemoryleak.ui.chat.UiChatMessage
import ru.zinoview.viewmodelmemoryleak.ui.chat.state.SaveState
import ru.zinoview.viewmodelmemoryleak.ui.chat.state.ToOldMessageMapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.state.UiState
import ru.zinoview.viewmodelmemoryleak.ui.chat.state.UiStateViewModel
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.SnackBar
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.ViewWrapper
import ru.zinoview.viewmodelmemoryleak.ui.core.Show

interface MessageSession : Disconnect<Unit>, EditContent, Show<Unit>, SaveState {

    fun addMessage(message: UiChatMessage) = Unit

    fun sendMessage(viewModel: ChatViewModel, content: String) = Unit


    override fun show(arg: Unit) = Unit
    override fun editContent(content: String) = Unit
    override fun disconnect(arg: Unit) = Unit
    override fun saveState(viewModel: UiStateViewModel, editText: UiState.EditText) = Unit

    class Base(
        private val viewWrapper: ViewWrapper,
        private val editTextWrapper: ViewWrapper,
        private val snackBar: SnackBar<Unit>,
        private val editedMapper: ToEditedMessageMapper,
        private val oldMapper: ToOldMessageMapper,
    ) : MessageSession {

        private var editedMessage: UiChatMessage.EditedMessage = UiChatMessage.EditedMessage.Empty
        private var oldMessage: UiChatMessage.OldMessage = UiChatMessage.OldMessage.Empty

        override fun addMessage(message: UiChatMessage) {
            this.editedMessage = message.map(editedMapper)
            this.oldMessage = message.map(oldMapper)
        }

        override fun saveState(viewModel: UiStateViewModel, editText: UiState.EditText)
            = oldMessage.saveState(viewModel, editText)
        override fun editContent(content: String) = editedMessage.editContent(content)

        override fun sendMessage(viewModel: ChatViewModel,content: String) {
            editTextWrapper.disconnect(Unit)
            if (editedMessage.isNotEmpty(Unit)) {
                editedMessage.editContent(content)
                editedMessage.doAction(viewModel)
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
            editedMessage = UiChatMessage.EditedMessage.Empty
            oldMessage = UiChatMessage.OldMessage.Empty
            viewWrapper.disconnect(Unit)
        }
    }

    object Empty : MessageSession
}