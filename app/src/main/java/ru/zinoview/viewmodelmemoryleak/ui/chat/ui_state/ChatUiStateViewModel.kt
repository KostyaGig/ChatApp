package ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state

import androidx.lifecycle.viewModelScope
import ru.zinoview.viewmodelmemoryleak.data.chat.ui_state.ChatUiStateRepository
import ru.zinoview.viewmodelmemoryleak.ui.chat.DomainToUiMessageMapper
import ru.zinoview.viewmodelmemoryleak.ui.core.ui_state.UiStateCommunication
import ru.zinoview.viewmodelmemoryleak.ui.core.ui_state.UiStateViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.ui_state.UiStateWork

class ChatUiStateViewModel(
    private val work: UiStateWork<ChatUiStates>,
    private val repository: ChatUiStateRepository,
    private val communication: UiStateCommunication,
    private val mapper: DomainToUiMessageMapper,
    private val uiStatesMapper: ChatUiStatesMapper
) : UiStateViewModel<ChatUiStates.Base, ChatUiStates>(
    repository, communication
) {

    override fun read(key: Unit) = work.execute(viewModelScope, {
        val dataMessages = repository.messages()
        val uiMessages = dataMessages.map { it.map(mapper) }

        val uiState = repository.read(Unit) { it.map() }

        uiState.map(uiStatesMapper, uiMessages)
    }, { uiStates ->
        uiStates.map(communication)
    })

}


