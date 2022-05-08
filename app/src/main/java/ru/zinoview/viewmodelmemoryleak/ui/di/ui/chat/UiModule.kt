package ru.zinoview.viewmodelmemoryleak.ui.di.ui.chat

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.ui.chat.*
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.UiStateCommunication
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.UiStateViewModel
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.UiStateWork
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.UiStatesMapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.user_status.UiMessagesNotificationCommunication
import ru.zinoview.viewmodelmemoryleak.ui.chat.user_status.UserStatusViewModel
import ru.zinoview.viewmodelmemoryleak.ui.connection.ConnectionCommunication
import ru.zinoview.viewmodelmemoryleak.ui.connection.ConnectionViewModel
import ru.zinoview.viewmodelmemoryleak.ui.connection.DataToUiConnectionMapper
import ru.zinoview.viewmodelmemoryleak.ui.connection.NetworkConnectionWork
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module


class UiModule : Module {

    private val uiStateChatModule = module {
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

    private val uiUserStatusChatModule = module {
        scope(SCOPE_NAME) {
            UserStatusViewModel.Base(
                get(),
                UiMessagesNotificationCommunication(),
                get()
            )
        }
    }

    private val uiChatModule = module {
        scope(SCOPE_NAME) {
            ChatViewModel.Base(
                get(),
                ChatWork.Base(
                    get(),
                    get(),
                ),
                get(),
                get(),
                MessagesCommunication(),
                Scroll.Base(ScrollCommunication()),
            )
        }

        single<DomainToUiMessageMapper> {
            DomainToUiMessageMapper.Base(
                get()
            )
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
         modules.add(uiStateChatModule)
         modules.add(uiUserStatusChatModule)
         modules.add(uiChatModule)
    }

    private companion object {
        const val SCOPE_NAME = "cufScope"
    }
}