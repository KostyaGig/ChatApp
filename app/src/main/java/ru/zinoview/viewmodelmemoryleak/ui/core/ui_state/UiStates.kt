package ru.zinoview.viewmodelmemoryleak.ui.core.ui_state

import ru.zinoview.viewmodelmemoryleak.core.Mapper
import ru.zinoview.viewmodelmemoryleak.ui.core.Communication

interface UiStates : Mapper.Unit<Communication<List<UiState>>> {

    override fun map(src: Communication<List<UiState>>) = Unit
}
