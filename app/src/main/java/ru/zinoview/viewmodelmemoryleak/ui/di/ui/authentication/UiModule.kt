package ru.zinoview.viewmodelmemoryleak.ui.di.ui.authentication

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.ui.authentication.AuthenticationCommunication
import ru.zinoview.viewmodelmemoryleak.ui.authentication.AuthenticationViewModel
import ru.zinoview.viewmodelmemoryleak.ui.authentication.DataToUiAuthMapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.ChatViewModel
import ru.zinoview.viewmodelmemoryleak.ui.chat.DataToUiMessageMapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.MessagesCommunication
import ru.zinoview.viewmodelmemoryleak.ui.connection.ConnectionCommunication
import ru.zinoview.viewmodelmemoryleak.ui.connection.DataToUiConnectionMapper
import ru.zinoview.viewmodelmemoryleak.ui.connection.UiConnectionWrapper
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.ExtraToTypeFragmentMapper
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.Intent
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.StringFragment
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.TypeFragmentToExtraMapper
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module

class UiModule : Module {
    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(uiChatModule)
    }
    private val uiChatModule = module {
        single {
            AuthenticationViewModel.Base(
                get(),
                AuthenticationCommunication(),
                DataToUiAuthMapper(),
            )
        }

        single<StringFragment> {
            StringFragment.Base()
        }

        single<TypeFragmentToExtraMapper> {
            TypeFragmentToExtraMapper.Base(
                get()
            )
        }

        single<Intent<String>> {
            Intent.Fragment(
                ExtraToTypeFragmentMapper.Base(
                    get()
                )
            )
        }

    }
}