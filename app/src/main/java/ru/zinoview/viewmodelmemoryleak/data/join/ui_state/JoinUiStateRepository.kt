package ru.zinoview.viewmodelmemoryleak.data.join.ui_state

import ru.zinoview.viewmodelmemoryleak.data.core.ui_state.UiStateRepository
import ru.zinoview.viewmodelmemoryleak.data.core.ui_state.UiStateSharedPreferences
import ru.zinoview.viewmodelmemoryleak.ui.join.ui_state.JoinUiStates

interface JoinUiStateRepository : UiStateRepository<JoinUiStates.Base,JoinUiStates> {

    class Base(
        prefs: UiStateSharedPreferences<JoinUiStates.Base,JoinUiStates>,
    ) : JoinUiStateRepository, UiStateRepository.Base<JoinUiStates.Base,JoinUiStates>(prefs)
}