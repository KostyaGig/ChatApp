package ru.zinoview.viewmodelmemoryleak.ui.di.data.authentication

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.data.authentication.AuthenticationRepository
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module

class DataModule : Module {
    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(dataModule)
    }

    private val dataModule = module {
        single<AuthenticationRepository> {
            AuthenticationRepository.Base(
                get()
            )
        }
    }
}