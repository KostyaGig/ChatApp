package ru.zinoview.viewmodelmemoryleak.ui.di.ui.chat

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.ui.chat.*
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module


class UiModule : Module {
    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(uiChatModule)
    }
    private val uiChatModule = module {
        scope(SCOPE_NAME) {
            ChatViewModel.Base(
                get(),
                ChatWork.Base(
                    get(),
                    ToUiMessageMapper(),
                ),
                get(),
                ToUiMessageMapper(),
                MessagesCommunication(),
                ScrollCommunication(),
                get()
            )
        }
    }

    private companion object {
        private const val SCOPE_NAME = "cufScope"
    }
}