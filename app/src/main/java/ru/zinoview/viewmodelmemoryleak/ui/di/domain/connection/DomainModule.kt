package ru.zinoview.viewmodelmemoryleak.ui.di.domain.connection

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.domain.connection.ConnectionInteractor
import ru.zinoview.viewmodelmemoryleak.domain.connection.DataToDomainConnectionMapper
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module

class DomainModule : Module {

    private val domainModule = module {
        single<ConnectionInteractor> {
            ConnectionInteractor.Base(
                get(),
                get(),
                DataToDomainConnectionMapper.Base()
            )
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(domainModule)
    }
}