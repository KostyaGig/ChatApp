package ru.zinoview.viewmodelmemoryleak.ui.core.ui_state

import ru.zinoview.viewmodelmemoryleak.core.Mapper
import ru.zinoview.viewmodelmemoryleak.core.Read
import ru.zinoview.viewmodelmemoryleak.core.Save
import ru.zinoview.viewmodelmemoryleak.data.core.ui_state.UiStateRepository
import ru.zinoview.viewmodelmemoryleak.ui.core.BaseViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.Communication
import ru.zinoview.viewmodelmemoryleak.ui.core.CommunicationObserve

abstract class UiStateViewModel<T : Mapper.Unit<Communication<List<UiState>>>,R>(
    private val repository: UiStateRepository<T, R>,
    communication: UiStateCommunication,
) : BaseViewModel<List<UiState>>(communication), Save<T>,
    CommunicationObserve<List<UiState>>, Read<Unit,Unit> {

    override fun save(state: T) = repository.save(state)
}
