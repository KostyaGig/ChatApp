package ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state

import ru.zinoview.viewmodelmemoryleak.ui.chat.DomainToUiMessageMapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.UiMessage

// todo move to core -> ui_state
interface UiStates {

    fun map(communication: UiStateCommunication) = Unit
    fun map(mapper: DomainToUiMessageMapper) : UiStates = Base()
    fun map(mapper: UiStatesMapper,messages: List<UiMessage>) : UiStates = Test.Empty

    class Base(
        private val messageField: UiState.EditText = UiState.EditText(),
        private val editMessage: UiState.MessageSession = UiState.MessageSession(),
        private val messages: UiState.Messages = UiState.Messages()
    ) : UiStates {

        override fun map(communication: UiStateCommunication) {
            val states = mutableListOf<UiState>()

            if (messageField.isNotEmpty(Unit)) {
                states.add(messageField)
            }

            if (editMessage.isNotEmpty(Unit)) {
                states.add(editMessage)
            }

            if (messages.isNotEmpty(Unit)) {
                states.add(messages)
            }

            communication.postValue(states)
        }

        override fun map(mapper: UiStatesMapper,messages: List<UiMessage>)
            = mapper.map(
                listOf(messageField,editMessage),messages
            )

    }

    interface Test : UiStates {

        fun map() : UiStates = Empty

        data class Base(
            private val messageField: UiState.EditText = UiState.EditText(),
            private val editMessage: UiState.MessageSession = UiState.MessageSession()
        ) : Test {

            override fun map() : UiStates {
                return if (messageField.isNotEmpty(Unit) && editMessage.isNotEmpty(Unit)) {
                    this
                } else {
                    return if (messageField.isNotEmpty(Unit) && editMessage.isNotEmpty(Unit).not()) {
                        Base(messageField, editMessage)
                    } else if (messageField.isNotEmpty(Unit).not() && editMessage.isNotEmpty(Unit)){
                        Base(messageField, editMessage)
                    } else {
                        Empty
                    }
                }
            }
        }

        object Empty : Test
    }

}