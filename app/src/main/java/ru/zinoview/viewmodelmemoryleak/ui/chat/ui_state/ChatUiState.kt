package ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.core.IsNotEmpty
import ru.zinoview.viewmodelmemoryleak.core.Mapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.UiMessage
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.ViewWrapper
import ru.zinoview.viewmodelmemoryleak.ui.core.Adapter

interface ChatUiState : IsNotEmpty<Unit>, ru.zinoview.viewmodelmemoryleak.ui.core.ui_state.UiState {

    data class EditText(
        private val text: String = ""
    ) : ChatUiState {

        override fun recover(
            editText: ViewWrapper,
            viewWrapper: ViewWrapper,
            messageSession: ru.zinoview.viewmodelmemoryleak.ui.chat.edit.MessageSession,
            adapter: Adapter<List<UiMessage>>
        ) = editText.show(Unit,text)

        override fun isNotEmpty(arg: Unit)
            = text.isNotEmpty()
    }

    data class MessageSession(
        private val oldMessageText: String = "",
        private val messageId: String = ""
    ) : ChatUiState {

        override fun recover(
            editText: ViewWrapper,
            viewWrapper: ViewWrapper,
            messageSession: ru.zinoview.viewmodelmemoryleak.ui.chat.edit.MessageSession,
            adapter: Adapter<List<UiMessage>>
        ) {
            viewWrapper.show(Unit,oldMessageText)

            val uiChatMessage = UiMessage.OldMessage.Base(messageId,oldMessageText)
            messageSession.add(uiChatMessage)

            Log.d("zinoviewk","recover old message $oldMessageText")

            messageSession.show(Unit)
        }

        override fun isNotEmpty(arg: Unit)
            = oldMessageText.isNotEmpty() && messageId.isNotEmpty()
    }

    class Messages(
        private val messages: List<UiMessage> = emptyList()
    ) : ChatUiState {

        override fun recover(
            editText: ViewWrapper,
            viewWrapper: ViewWrapper,
            messageSession: ru.zinoview.viewmodelmemoryleak.ui.chat.edit.MessageSession,
            adapter: Adapter<List<UiMessage>>
        ) = adapter.update(messages)

        override fun isNotEmpty(arg: Unit): Boolean = messages.isNotEmpty()
    }

    object Empty : ChatUiState {

        override fun recover(
            editText: ViewWrapper,
            viewWrapper: ViewWrapper,
            messageSession: ru.zinoview.viewmodelmemoryleak.ui.chat.edit.MessageSession,
            adapter: Adapter<List<UiMessage>>
        ) = Unit

        override fun isNotEmpty(arg: Unit) = false
    }

}