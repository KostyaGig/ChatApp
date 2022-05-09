package ru.zinoview.viewmodelmemoryleak.ui.di.ui.chat

import kotlinx.coroutines.CoroutineScope
import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.core.Time
import ru.zinoview.viewmodelmemoryleak.ui.chat.*
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.UiStateCommunication
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.UiStateViewModel
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.UiStateWork
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.UiStatesMapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.user_status.UiMessagesNotificationCommunication
import ru.zinoview.viewmodelmemoryleak.ui.chat.user_status.UserStatusViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.Dispatcher
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module

class UiModule(
    private val scope: CoroutineScope
) : Module {

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

        factory<TypeMessageTextWatcher> {
            TypeMessageTextWatcher.Base(
                TypeMessageTimer.Base(get(),Time.Base()),
                Dispatcher.Delay(),
                scope
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