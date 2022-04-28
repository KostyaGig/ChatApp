package ru.zinoview.viewmodelmemoryleak.ui.di.ui.chat.ui_state

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.UiStateCommunication
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.UiStateViewModel
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.UiStateWork
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.UiStatesMapper
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module

class UiModule : Module {
    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(uiChatModule)
    }
    private val uiChatModule = module {
        scope(SCOPE_NAME) {
            UiStateViewModel.Base(
                UiStateWork(get()),
                get(),
                UiStateCommunication(),
                get(),
                UiStatesMapper.Base()
            )
        }
    }

    private companion object {
        private const val SCOPE_NAME = "cufScope"
    }
}