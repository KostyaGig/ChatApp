package ru.zinoview.viewmodelmemoryleak.ui.chat.state

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.zinoview.viewmodelmemoryleak.core.Read
import ru.zinoview.viewmodelmemoryleak.core.Save
import ru.zinoview.viewmodelmemoryleak.data.chat.state.UiStateRepository
import ru.zinoview.viewmodelmemoryleak.ui.core.CommunicationObserve
import ru.zinoview.viewmodelmemoryleak.ui.core.Work

interface UiStateViewModel : CommunicationObserve<List<UiState>>, Save<UiStates>, Read<Unit,Unit> {

    class Base(
        private val work: Work<UiStates,UiStates>,
        private val repository: UiStateRepository,
        private val communication: UiStateCommunication
    ) : UiStateViewModel, ViewModel() {

        override fun save(state: UiStates)
            = work.doBackground(viewModelScope) {
                repository.save(state)
            }

        override fun observe(owner: LifecycleOwner, observer: Observer<List<UiState>>)
            = communication.observe(owner, observer)

        override fun read(key: Unit) {
            work.execute(viewModelScope, {
               repository.read(Unit)
            }, { uiStates ->
                uiStates.map(communication)
            })
        }
    }

}