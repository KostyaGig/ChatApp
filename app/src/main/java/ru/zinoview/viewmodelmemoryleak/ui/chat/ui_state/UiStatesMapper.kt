package ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state

import ru.zinoview.viewmodelmemoryleak.ui.chat.UiMessage

interface UiStatesMapper {

    fun map(states: List<UiState>,messages: List<UiMessage>) : UiStates

    class Base : UiStatesMapper {

        override fun map(states: List<UiState>, messages: List<UiMessage>): UiStates {

            return UiStates.Base(
                states[MESSAGE_FIELD_STATE_INDEX] as UiState.EditText,
                states[EDIT_MESSAGE_STATE_INDEX] as UiState.MessageSession,
                UiState.Messages(messages)
            )
        }

        private companion object {
            private const val MESSAGE_FIELD_STATE_INDEX = 0
            private const val EDIT_MESSAGE_STATE_INDEX = 1
        }
    }
}