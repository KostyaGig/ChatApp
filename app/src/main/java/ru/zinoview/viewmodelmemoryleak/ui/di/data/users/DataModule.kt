package ru.zinoview.viewmodelmemoryleak.ui.di.data.users

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.data.core.ExceptionMapper
import ru.zinoview.viewmodelmemoryleak.data.users.UsersRepository
import ru.zinoview.viewmodelmemoryleak.data.users.cloud.CloudMessageMapper
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module

class DataModule : Module {

    private val dataModule = module {

        single<UsersRepository> {
            UsersRepository.Base(
                get(),
                CloudMessageMapper.Base(),
                get(),
                ExceptionMapper.Abstract.User(get()),
                get(),
                get()
            )
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(dataModule)
    }
}