package ru.zinoview.viewmodelmemoryleak.ui.chat.state

interface UiStates {

    fun map(communication: UiStateCommunication) = Unit

    class Base(
        private val messageField: UiState.EditText = UiState.EditText(),
        private val editMessage: UiState.MessageSession = UiState.MessageSession()
    ) : UiStates {

        override fun map(communication: UiStateCommunication) {
            val states = mutableListOf<UiState>()

            if (messageField.isNotEmpty(Unit)) {
                states.add(messageField)
            }

            if (editMessage.isNotEmpty(Unit)) {
                states.add(editMessage)
            }
            communication.postValue(states)
        }
    }

    interface Test : UiStates {

        fun map() : UiStates = UiStates.Test.Empty

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