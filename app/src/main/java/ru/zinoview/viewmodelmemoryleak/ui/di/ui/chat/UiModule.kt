package ru.zinoview.viewmodelmemoryleak.ui.di.ui.chat

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.ui.chat.ChatViewModel
import ru.zinoview.viewmodelmemoryleak.ui.chat.ChatWork
import ru.zinoview.viewmodelmemoryleak.ui.chat.DataToUiMessageMapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.MessagesCommunication
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
                    DataToUiMessageMapper(),
                ),
                get(),
                DataToUiMessageMapper(),
                MessagesCommunication(),
                get()
            )
        }
    }

    private companion object {
        private const val SCOPE_NAME = "cufScope"
    }
}