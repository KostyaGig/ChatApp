package ru.zinoview.viewmodelmemoryleak.ui.di.data.join

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.data.join.JoinUserRepository
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module

class DataModule : Module {

    private val dataModule = module {

        single<JoinUserRepository> {
            JoinUserRepository.Base(
                get(),
                get(),
                get()
            )
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(dataModule)
    }
}