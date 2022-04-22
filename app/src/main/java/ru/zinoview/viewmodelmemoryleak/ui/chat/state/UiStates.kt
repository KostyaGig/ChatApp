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
}