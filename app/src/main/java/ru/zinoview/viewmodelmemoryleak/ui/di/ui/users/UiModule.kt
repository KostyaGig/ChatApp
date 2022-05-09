package ru.zinoview.viewmodelmemoryleak.ui.di.ui.users

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.UiStateCommunication
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module
import ru.zinoview.viewmodelmemoryleak.ui.join.DataToUiJoinMapper
import ru.zinoview.viewmodelmemoryleak.ui.join.JoinUserCommunication
import ru.zinoview.viewmodelmemoryleak.ui.join.JoinUserViewModel
import ru.zinoview.viewmodelmemoryleak.ui.join.JoinWork
import ru.zinoview.viewmodelmemoryleak.ui.users.AbstractToUiUserMapper
import ru.zinoview.viewmodelmemoryleak.ui.users.DomainToUiUserMapper
import ru.zinoview.viewmodelmemoryleak.ui.users.UsersCommunication
import ru.zinoview.viewmodelmemoryleak.ui.users.UsersViewModel


class UiModule : Module {
    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(uiChatModule)
    }
    private val uiChatModule = module {
        scope(SCOPE_NAME) {
            UsersViewModel.Base(
                get(),
                get(),
                DomainToUiUserMapper.Base(
                    AbstractToUiUserMapper.Base(),
                    get()
                ),
                UsersCommunication()
            )
        }
    }

    private companion object {
        private const val SCOPE_NAME = "usfScope"
    }
}