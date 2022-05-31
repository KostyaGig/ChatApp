package ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state

import ru.zinoview.viewmodelmemoryleak.ui.chat.UiMessage

interface ChatUiStatesMapper {

    fun map(states: List<ChatUiState>, messages: List<UiMessage>) : ChatUiStates

    class Base : ChatUiStatesMapper {

        override fun map(states: List<ChatUiState>, messages: List<UiMessage>): ChatUiStates {

            return ChatUiStates.Base(
                states[MESSAGE_FIELD_STATE_INDEX] as ChatUiState.EditText,
                states[EDIT_MESSAGE_STATE_INDEX] as ChatUiState.MessageSession,
                ChatUiState.Messages(messages)
            )
        }

        private companion object {
            private const val MESSAGE_FIELD_STATE_INDEX = 0
            private const val EDIT_MESSAGE_STATE_INDEX = 1
        }
    }
}