package ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state

import ru.zinoview.viewmodelmemoryleak.data.core.ui_state.AbstractModel
import ru.zinoview.viewmodelmemoryleak.ui.chat.UiMessage
import ru.zinoview.viewmodelmemoryleak.ui.core.Communication
import ru.zinoview.viewmodelmemoryleak.ui.core.ui_state.UiState
import ru.zinoview.viewmodelmemoryleak.ui.core.ui_state.UiStates

interface ChatUiStates : UiStates, AbstractModel<ChatUiStates> {

    fun map(mapper: ChatUiStatesMapper, messages: List<UiMessage>) : ChatUiStates = Empty
    override fun map(): ChatUiStates = this

    open class Base(
        private val messageField: ChatUiState.EditText = ChatUiState.EditText(),
        private val editMessage: ChatUiState.MessageSession = ChatUiState.MessageSession(),
        private val messages: ChatUiState.Messages = ChatUiState.Messages()
    ) : ChatUiStates {

        override fun map(communication: Communication<List<UiState>>) {
            val states = mutableListOf<ChatUiState>()

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

        override fun map(mapper: ChatUiStatesMapper, messages: List<UiMessage>)
                = mapper.map(
            listOf(messageField,editMessage),messages
        )

        data class Test(
            private val messageField: ChatUiState.EditText = ChatUiState.EditText(),
            private val editMessage: ChatUiState.MessageSession = ChatUiState.MessageSession()
        ) : Base() {

            override fun map() : ChatUiStates {
                return if (messageField.isNotEmpty(Unit) && editMessage.isNotEmpty(Unit)) {
                    this
                } else {
                    return if (messageField.isNotEmpty(Unit) && editMessage.isNotEmpty(Unit).not()) {
                        Test(messageField, editMessage)
                    } else if (messageField.isNotEmpty(Unit).not() && editMessage.isNotEmpty(Unit)){
                        Test(messageField, editMessage)
                    } else {
                        Empty
                    }
                }
            }
        }

    }

    object Empty : ChatUiStates

}