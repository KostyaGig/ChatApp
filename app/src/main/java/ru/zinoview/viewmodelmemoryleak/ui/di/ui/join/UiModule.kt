package ru.zinoview.viewmodelmemoryleak.ui.di.ui.join

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module
import ru.zinoview.viewmodelmemoryleak.ui.join.DataToUiJoinMapper
import ru.zinoview.viewmodelmemoryleak.ui.join.JoinUserCommunication
import ru.zinoview.viewmodelmemoryleak.ui.join.JoinUserViewModel
import ru.zinoview.viewmodelmemoryleak.ui.join.JoinWork


class UiModule : Module {
    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(uiChatModule)
    }
    private val uiChatModule = module {
        scope(SCOPE_NAME) {
            JoinUserViewModel.Base(
                get(),
                JoinWork.Base(
                    get(),
                    DataToUiJoinMapper()
                ),
                JoinUserCommunication(),
                get()
            )
        }
    }

    private companion object {
        private const val SCOPE_NAME = "jufScope"
    }
}