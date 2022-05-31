package ru.zinoview.viewmodelmemoryleak.ui.join.ui_state

import androidx.lifecycle.viewModelScope
import ru.zinoview.viewmodelmemoryleak.data.join.ui_state.JoinUiStateRepository
import ru.zinoview.viewmodelmemoryleak.ui.core.ui_state.UiStateWork
import ru.zinoview.viewmodelmemoryleak.ui.core.ui_state.UiStateCommunication
import ru.zinoview.viewmodelmemoryleak.ui.core.ui_state.UiStateViewModel


class JoinUiStateViewModel(
    private val work: UiStateWork<JoinUiStates>,
    private val repository: JoinUiStateRepository,
    private val communication: UiStateCommunication
) : UiStateViewModel<JoinUiStates.Base,JoinUiStates>(repository, communication) {

    override fun read(key: Unit) {
        work.execute(viewModelScope,{
            repository.read(key) {it.map()}
        },{ ui ->
            ui.map(communication)
        })
    }
}
