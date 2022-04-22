package ru.zinoview.viewmodelmemoryleak.ui.chat.state

import ru.zinoview.viewmodelmemoryleak.core.IsNotEmpty
import ru.zinoview.viewmodelmemoryleak.ui.chat.UiEditChatMessage
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.ViewWrapper

interface UiState : IsNotEmpty<Unit> {

    fun recover(editText: android.widget.EditText, messageSession: ru.zinoview.viewmodelmemoryleak.ui.chat.edit.MessageSession, viewWrapper: ViewWrapper)

    class EditText(
        private val text: String = ""
    ) : UiState {

        override fun recover(
            editText: android.widget.EditText,
            messageSession: ru.zinoview.viewmodelmemoryleak.ui.chat.edit.MessageSession,
            viewWrapper: ViewWrapper
        ) {
            editText.setText(text)

        }

        override fun isNotEmpty(arg: Unit)
            = text.isNotEmpty()
    }


    data class MessageSession(
        private val oldMessageText: String = "",
        private val messageId: String = ""
    ) : UiState {

        override fun recover(
            editText: android.widget.EditText,
            messageSession: ru.zinoview.viewmodelmemoryleak.ui.chat.edit.MessageSession,
            viewWrapper: ViewWrapper
        ) {
            viewWrapper.show(Unit,oldMessageText)

            // todo rewrite
            val editMessage = UiEditChatMessage.Base(messageId)
            messageSession.addMessage(editMessage)

            messageSession.show(Unit)
        }

        override fun isNotEmpty(arg: Unit)
            = oldMessageText.isNotEmpty() && messageId.isNotEmpty()
    }

}