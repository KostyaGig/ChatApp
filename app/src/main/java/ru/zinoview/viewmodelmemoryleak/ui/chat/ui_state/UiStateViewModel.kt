package ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.zinoview.viewmodelmemoryleak.core.Read
import ru.zinoview.viewmodelmemoryleak.core.Save
import ru.zinoview.viewmodelmemoryleak.data.chat.ui_state.UiStateRepository
import ru.zinoview.viewmodelmemoryleak.ui.chat.ToUiMessageMapper
import ru.zinoview.viewmodelmemoryleak.ui.core.CommunicationObserve
import ru.zinoview.viewmodelmemoryleak.ui.core.Work

interface UiStateViewModel : CommunicationObserve<List<UiState>>, Save<UiStates>, Read<Unit,Unit> {

    class Base(
        private val work: Work<UiStates,UiStates>,
        private val repository: UiStateRepository,
        private val communication: UiStateCommunication,
        private val mapper: ToUiMessageMapper,
        private val uiStatesMapper: UiStatesMapper
    ) : UiStateViewModel, ViewModel() {

        override fun save(state: UiStates)
            = work.doBackground(viewModelScope) {
                repository.save(state)
            }

        override fun observe(owner: LifecycleOwner, observer: Observer<List<UiState>>)
            = communication.observe(owner, observer)

        override fun read(key: Unit) {
            work.execute(viewModelScope, {
                val dataMessages = repository.messages()
                val uiMessages = dataMessages.map { it.map(mapper) }

                val uiState = repository.read(Unit)

                uiState.map(uiStatesMapper,uiMessages)
            }, { uiStates ->
                uiStates.map(communication)
            })
        }
    }

}