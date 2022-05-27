package ru.zinoview.viewmodelmemoryleak.ui.join.ui_state

import androidx.lifecycle.viewModelScope
import ru.zinoview.viewmodelmemoryleak.core.Read
import ru.zinoview.viewmodelmemoryleak.core.Save
import ru.zinoview.viewmodelmemoryleak.data.chat.ui_state.UiStateRepository
import ru.zinoview.viewmodelmemoryleak.ui.core.BaseViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.CommunicationObserve
import ru.zinoview.viewmodelmemoryleak.ui.core.Dispatcher

// todo create abstract viewmodel for this and UiStateViewModel which belongs to ChatFragment
interface UiStateJoinViewModel : CommunicationObserve<List<JoinUiState>>, Save<JoinUiStates.Base>,
    Read<Unit, Unit> {

    class Base(
        private val dispatcher: Dispatcher,
        private val repository: UiStateRepository.Join,
        private val communication: JoinUiStateCommunication
    ) : UiStateJoinViewModel, BaseViewModel<List<JoinUiState>>(communication) {

        override fun save(state: JoinUiStates.Base) = dispatcher.doBackground(viewModelScope) {
            repository.save(state)
        }

        override fun read(key: Unit) {
            dispatcher.doBackground(viewModelScope) {
                val states = repository.read(Unit)

                dispatcher.doUi(viewModelScope) {
                    states.map(communication)
                }
            }
        }

    }

}