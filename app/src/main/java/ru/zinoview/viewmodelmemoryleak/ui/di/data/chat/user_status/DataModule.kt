package ru.zinoview.viewmodelmemoryleak.ui.di.data.chat.user_status

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.data.chat.user_status.UserStatusRepository
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module

class DataModule : Module {

    private val dataModule = module {
        single<UserStatusRepository> {
            UserStatusRepository.Base(get())
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(dataModule)
    }
}