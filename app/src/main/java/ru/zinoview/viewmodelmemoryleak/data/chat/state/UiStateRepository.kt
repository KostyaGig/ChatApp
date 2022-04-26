package ru.zinoview.viewmodelmemoryleak.data.chat.state

import ru.zinoview.viewmodelmemoryleak.core.Read
import ru.zinoview.viewmodelmemoryleak.core.Save
import ru.zinoview.viewmodelmemoryleak.data.chat.ChatRepository
import ru.zinoview.viewmodelmemoryleak.ui.chat.state.UiStates

interface UiStateRepository : Save<UiStates>, Read<UiStates,Unit> {

    class Base(
        private val prefs: UiStateSharedPreferences,
        private val chatRepository: ChatRepository<Unit>
    ) : UiStateRepository {

        override fun save(state: UiStates) = chatRepository.saveState(prefs,state)

        override fun read(key: Unit) =  prefs.read(Unit)
    }

    class Test : UiStateRepository {

        private var uiState: UiStates = UiStates.Test.Empty

        override fun save(state: UiStates) {
            uiState = state
        }

        override fun read(key: Unit) = (uiState as UiStates.Test).map()
    }
}