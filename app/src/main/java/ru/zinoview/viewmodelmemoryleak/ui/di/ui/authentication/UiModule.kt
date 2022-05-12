package ru.zinoview.viewmodelmemoryleak.ui.di.ui.authentication

import android.content.Context
import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.data.cache.SharedPreferencesReader
import ru.zinoview.viewmodelmemoryleak.ui.authentication.AuthWorker
import ru.zinoview.viewmodelmemoryleak.ui.authentication.AuthenticationCommunication
import ru.zinoview.viewmodelmemoryleak.ui.authentication.AuthenticationViewModel
import ru.zinoview.viewmodelmemoryleak.ui.authentication.DataToUiAuthMapper
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.*
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module

class UiModule(
    private val context: Context
) : Module {
    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(uiChatModule)
    }
    private val uiChatModule = module {
        scope(SCOPE_NAME) {
            AuthenticationViewModel.Base(
                get(),
                AuthWorker.Base(
                    get(),
                    DataToUiAuthMapper()
                ),
                AuthenticationCommunication(),
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
            val stringFragment =  StringFragment.Base()
            val mapper = ExtraToTypeFragmentMapper.Base(
                stringFragment
            )
            Intent.Fragment(
                mapper,
                Intent.FragmentStore.Base(
                    stringFragment,
                    mapper,
                    FragmentSharedPreferences.Base(
                        context, SharedPreferencesReader.String(
                            get()
                        )
                    )
                )
            )
        }
    }

    private companion object {
        private const val SCOPE_NAME = "authScope"
    }
}