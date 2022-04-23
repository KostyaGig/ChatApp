package ru.zinoview.viewmodelmemoryleak.ui.chat.state

import ru.zinoview.viewmodelmemoryleak.core.IsNotEmpty
import ru.zinoview.viewmodelmemoryleak.ui.chat.UiChatMessage
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.ViewWrapper

interface UiState : IsNotEmpty<Unit> {

    fun recover(editText: ViewWrapper, viewWrapper: ViewWrapper, messageSession: ru.zinoview.viewmodelmemoryleak.ui.chat.edit.MessageSession)

    data class EditText(
        private val text: String = ""
    ) : UiState {

        override fun recover(
            editText: ViewWrapper,
            viewWrapper: ViewWrapper,
            messageSession: ru.zinoview.viewmodelmemoryleak.ui.chat.edit.MessageSession
        ) {
            editText.show(Unit,text)
        }

        override fun isNotEmpty(arg: Unit)
            = text.isNotEmpty()
    }

    data class MessageSession(
        private val oldMessageText: String = "",
        private val messageId: String = ""
    ) : UiState {

        override fun recover(
            editText: ViewWrapper,
            viewWrapper: ViewWrapper,
            messageSession: ru.zinoview.viewmodelmemoryleak.ui.chat.edit.MessageSession,
        ) {
            viewWrapper.show(Unit,oldMessageText)

            val uiChatMessage = UiChatMessage.OldMessage.Base(messageId,oldMessageText)
            messageSession.addMessage(uiChatMessage)

            messageSession.show(Unit)
        }


        override fun isNotEmpty(arg: Unit)
            = oldMessageText.isNotEmpty() && messageId.isNotEmpty()
    }

    object Empty : UiState {

        override fun recover(
            editText: ViewWrapper,
            viewWrapper: ViewWrapper,
            messageSession: ru.zinoview.viewmodelmemoryleak.ui.chat.edit.MessageSession
        ) = Unit

        override fun isNotEmpty(arg: Unit) = false
    }

}