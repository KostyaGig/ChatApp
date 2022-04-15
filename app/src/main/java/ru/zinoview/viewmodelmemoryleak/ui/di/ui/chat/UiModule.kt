package ru.zinoview.viewmodelmemoryleak.ui.di.ui.chat

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.ui.chat.ChatViewModel
import ru.zinoview.viewmodelmemoryleak.ui.chat.DataToUiMessageMapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.MessagesCommunication
import ru.zinoview.viewmodelmemoryleak.ui.connection.ConnectionCommunication
import ru.zinoview.viewmodelmemoryleak.ui.connection.DataToUiConnectionMapper
import ru.zinoview.viewmodelmemoryleak.ui.connection.UiConnectionWrapper
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module


class UiModule : Module {
    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(uiChatModule)
    }
    private val uiChatModule = module {
        single {
            ChatViewModel.Base(
                get(),
                get(),
                DataToUiMessageMapper(),
                MessagesCommunication(),
                UiConnectionWrapper.Base(
                    get(),
                    get(),
                    ConnectionCommunication(),
                    DataToUiConnectionMapper()
                )
            )
        }
    }
}