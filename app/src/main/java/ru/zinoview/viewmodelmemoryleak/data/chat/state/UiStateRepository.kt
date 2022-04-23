package ru.zinoview.viewmodelmemoryleak.data.chat.state

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.core.Read
import ru.zinoview.viewmodelmemoryleak.core.Save
import ru.zinoview.viewmodelmemoryleak.ui.chat.state.UiStates

interface UiStateRepository : Save<UiStates>, Read<UiStates,Unit> {

    class Base(
        private val prefs: UiStateSharedPreferences
    ) : UiStateRepository {

        override fun save(state: UiStates) = prefs.save(state)
        override fun read(key: Unit): UiStates {
            val data = prefs.read(Unit)
            Log.d("zinoviewk","repo read $data")
            return data
        }
    }

    class Test : UiStateRepository {

        private var uiState: UiStates = UiStates.Test.Empty

        override fun save(state: UiStates) {
            uiState = state
        }

        override fun read(key: Unit) = (uiState as UiStates.Test).map()
    }
}