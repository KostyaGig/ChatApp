package ru.zinoview.viewmodelmemoryleak.ui.di.ui.join

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.ui.core.Dispatcher
import ru.zinoview.viewmodelmemoryleak.ui.core.ui_state.UiStateCommunication
import ru.zinoview.viewmodelmemoryleak.ui.core.ui_state.UiStateWork
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module
import ru.zinoview.viewmodelmemoryleak.ui.join.DataToUiJoinMapper
import ru.zinoview.viewmodelmemoryleak.ui.join.JoinUserCommunication
import ru.zinoview.viewmodelmemoryleak.ui.join.JoinUserViewModel
import ru.zinoview.viewmodelmemoryleak.ui.join.ui_state.JoinUiStateViewModel

class UiModule : Module {
    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(uiChatModule)
    }
    private val uiChatModule = module {
        scope(SCOPE_NAME) {
            JoinUserViewModel.Base(
                get(),
                Dispatcher.Base(),
                JoinUserCommunication(),
                DataToUiJoinMapper(),
            )
        }

        scope(SCOPE_NAME) {
            JoinUiStateViewModel(
                UiStateWork(get()),
                get(),
                UiStateCommunication()
            )
        }

    }

    private companion object {
        private const val SCOPE_NAME = "joinScope"
    }
}