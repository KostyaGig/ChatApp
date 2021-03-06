package ru.zinoview.viewmodelmemoryleak.ui.chat.edit

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.core.Add
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Disconnect
import ru.zinoview.viewmodelmemoryleak.ui.chat.ChatViewModel
import ru.zinoview.viewmodelmemoryleak.ui.chat.UiMessage
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.SaveState
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.ToOldMessageMapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.ChatUiState
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.ChatUiStateViewModel
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.SnackBar
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.ViewWrapper
import ru.zinoview.viewmodelmemoryleak.ui.core.Show
import ru.zinoview.viewmodelmemoryleak.ui.users.BundleUser

interface MessageSession : Disconnect<Unit>, EditContent, Show<Unit>, SaveState, Add<UiMessage> {

    fun sendMessage(viewModel: ChatViewModel,receiverId: String,content: String) = Unit

    override fun add(data: UiMessage) = Unit
    override fun show(arg: Unit) = Unit
    override fun editContent(content: String) = Unit
    override fun disconnect(arg: Unit) = Unit
    override fun saveState(viewModel: ChatUiStateViewModel, editText: ChatUiState.EditText,bundleUser: BundleUser) = Unit
    fun saveState(viewModel: ChatUiStateViewModel) = Unit

    class Base(
        private val viewWrapper: ViewWrapper,
        private val editTextWrapper: ViewWrapper,
        private val snackBar: SnackBar<Unit>,
        private val editedMapper: ToEditedMessageMapper,
        private val oldMapper: ToOldMessageMapper,
    ) : MessageSession {

        private var editedMessage: UiMessage.EditedMessage = UiMessage.EditedMessage.Empty
        private var oldMessage: UiMessage.OldMessage = UiMessage.OldMessage.Empty

        override fun add(message: UiMessage) {
            this.editedMessage = message.map(editedMapper)
            this.oldMessage = message.map(oldMapper)
        }

        override fun saveState(viewModel: ChatUiStateViewModel, editText: ChatUiState.EditText,bundleUser: BundleUser ) {
            oldMessage.saveState(viewModel, editText,bundleUser)
        }

        override fun saveState(viewModel: ChatUiStateViewModel) {
            oldMessage.saveState(viewModel,ChatUiState.EditText(),BundleUser.Empty)
        }

        override fun editContent(content: String) = editedMessage.editContent(content)

        override fun sendMessage(viewModel: ChatViewModel,receiverId:String,content: String) {
            editTextWrapper.disconnect(Unit)
            if (editedMessage.isNotEmpty(Unit)) {
                editedMessage.editContent(content)
                editedMessage.doAction(viewModel)
            }
            else {
                if (content.isNotEmpty()) {
                    viewModel.sendMessage(receiverId,content)
                } else {
                    snackBar.show(Unit)
                }
            }
            disconnect(Unit)
        }

        override fun show(arg: Unit) = viewWrapper.show(Unit)

        override fun disconnect(arg: Unit) {
            editedMessage = UiMessage.EditedMessage.Empty
            oldMessage = UiMessage.OldMessage.Empty
            viewWrapper.disconnect(Unit)
        }
    }

    object Empty : MessageSession
}