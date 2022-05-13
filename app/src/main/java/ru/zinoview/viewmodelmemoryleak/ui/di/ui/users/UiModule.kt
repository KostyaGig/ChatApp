package ru.zinoview.viewmodelmemoryleak.ui.di.ui.users

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.ui.core.UiText
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module
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
                    AbstractToUiUserMapper.Base(
                        UiText.Base(
                            30
                        )
                    ),
                    get()
                ),
                UsersCommunication()
            )
        }
    }

    private companion object {
        private const val SCOPE_NAME = "usersScope"
    }
}