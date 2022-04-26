package ru.zinoview.viewmodelmemoryleak.ui.chat.state

import ru.zinoview.viewmodelmemoryleak.core.IsNotEmpty
import ru.zinoview.viewmodelmemoryleak.ui.chat.ChatAdapter
import ru.zinoview.viewmodelmemoryleak.ui.chat.UiChatMessage
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.ViewWrapper

interface UiState : IsNotEmpty<Unit> {

    fun recover(
        editText: ViewWrapper,
        viewWrapper: ViewWrapper,
        messageSession: ru.zinoview.viewmodelmemoryleak.ui.chat.edit.MessageSession,
        adapter: ChatAdapter
    )

    data class EditText(
        private val text: String = ""
    ) : UiState {

        override fun recover(
            editText: ViewWrapper,
            viewWrapper: ViewWrapper,
            messageSession: ru.zinoview.viewmodelmemoryleak.ui.chat.edit.MessageSession,
            adapter: ChatAdapter
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
            adapter: ChatAdapter
        ) {
            viewWrapper.show(Unit,oldMessageText)

            val uiChatMessage = UiChatMessage.OldMessage.Base(messageId,oldMessageText)
            messageSession.add(uiChatMessage)

            messageSession.show(Unit)
        }


        override fun isNotEmpty(arg: Unit)
            = oldMessageText.isNotEmpty() && messageId.isNotEmpty()
    }

    class Messages(
        private val readMessages: List<UiChatMessage.Sent.Read> = emptyList(),
        private val unReadMessages: List<UiChatMessage.Sent.Unread> = emptyList(),
        private val receivedMessages: List<UiChatMessage.Received> = emptyList(),
        private val progressMessages: List<UiChatMessage.ProgressMessage> = emptyList(),
    ) : UiState {

        override fun recover(
            editText: ViewWrapper,
            viewWrapper: ViewWrapper,
            messageSession: ru.zinoview.viewmodelmemoryleak.ui.chat.edit.MessageSession,
            adapter: ChatAdapter
        ) {

        }


        override fun isNotEmpty(arg: Unit) = false
    }

    object Empty : UiState {

        override fun recover(
            editText: ViewWrapper,
            viewWrapper: ViewWrapper,
            messageSession: ru.zinoview.viewmodelmemoryleak.ui.chat.edit.MessageSession,
            adapter: ChatAdapter
        ) = Unit

        override fun isNotEmpty(arg: Unit) = false
    }

}