package ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state

import ru.zinoview.viewmodelmemoryleak.core.IsNotEmpty
import ru.zinoview.viewmodelmemoryleak.ui.chat.ChatAdapter
import ru.zinoview.viewmodelmemoryleak.ui.chat.UiMessage
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.ViewWrapper
import ru.zinoview.viewmodelmemoryleak.ui.core.Adapter

interface UiState : IsNotEmpty<Unit> {

    fun recover(
        editText: ViewWrapper,
        viewWrapper: ViewWrapper,
        messageSession: ru.zinoview.viewmodelmemoryleak.ui.chat.edit.MessageSession,
        adapter: Adapter<List<UiMessage>>
    )

    data class EditText(
        private val text: String = ""
    ) : UiState {

        override fun recover(
            editText: ViewWrapper,
            viewWrapper: ViewWrapper,
            messageSession: ru.zinoview.viewmodelmemoryleak.ui.chat.edit.MessageSession,
            adapter: Adapter<List<UiMessage>>
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
            adapter: Adapter<List<UiMessage>>
        ) {
            viewWrapper.show(Unit,oldMessageText)

            val uiChatMessage = UiMessage.OldMessage.Base(messageId,oldMessageText)
            messageSession.add(uiChatMessage)

            messageSession.show(Unit)
        }


        override fun isNotEmpty(arg: Unit)
            = oldMessageText.isNotEmpty() && messageId.isNotEmpty()
    }

    class Messages(
        private val messages: List<UiMessage> = emptyList()
    ) : UiState {

        override fun recover(
            editText: ViewWrapper,
            viewWrapper: ViewWrapper,
            messageSession: ru.zinoview.viewmodelmemoryleak.ui.chat.edit.MessageSession,
            adapter: Adapter<List<UiMessage>>
        ) = adapter.update(messages)

        override fun isNotEmpty(arg: Unit): Boolean = messages.isNotEmpty()
    }

    object Empty : UiState {

        override fun recover(
            editText: ViewWrapper,
            viewWrapper: ViewWrapper,
            messageSession: ru.zinoview.viewmodelmemoryleak.ui.chat.edit.MessageSession,
            adapter: Adapter<List<UiMessage>>
        ) = Unit

        override fun isNotEmpty(arg: Unit) = false
    }

}