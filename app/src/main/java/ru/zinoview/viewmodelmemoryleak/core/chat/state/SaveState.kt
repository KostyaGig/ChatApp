package ru.zinoview.viewmodelmemoryleak.core.chat.state

import ru.zinoview.viewmodelmemoryleak.data.chat.state.UiStateSharedPreferences
import ru.zinoview.viewmodelmemoryleak.ui.chat.state.UiStates

interface SaveState {

    fun saveState(prefs: UiStateSharedPreferences, state: UiStates)
}