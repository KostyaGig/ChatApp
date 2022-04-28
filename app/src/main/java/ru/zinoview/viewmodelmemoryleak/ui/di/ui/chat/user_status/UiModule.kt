package ru.zinoview.viewmodelmemoryleak.ui.di.ui.chat.user_status

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.ui.chat.user_status.UiMessagesNotificationCommunication
import ru.zinoview.viewmodelmemoryleak.ui.chat.user_status.UserStatusViewModel
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module

class UiModule : Module {
    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(uiChatModule)
    }
    private val uiChatModule = module {
        scope(SCOPE_NAME) {
            UserStatusViewModel.Base(
                get(),
                get(),
                UiMessagesNotificationCommunication()
            )
        }
    }

    // todo move const to abstract
    private companion object {
        private const val SCOPE_NAME = "cufScope"
    }
}