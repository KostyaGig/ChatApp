package ru.zinoview.viewmodelmemoryleak.ui.di.domain.users

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.domain.connection.ConnectionInteractor
import ru.zinoview.viewmodelmemoryleak.domain.connection.DataToDomainConnectionMapper
import ru.zinoview.viewmodelmemoryleak.domain.users.DataToDomainUsersMapper
import ru.zinoview.viewmodelmemoryleak.domain.users.UsersInteractor
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module

class DomainModule : Module {

    private val domainModule = module {
        single<UsersInteractor> {
            UsersInteractor.Base(
                get(),
                DataToDomainUsersMapper.Base()
            )
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(domainModule)
    }
}