package ru.zinoview.viewmodelmemoryleak.ui.di.data.connection

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.data.connection.CloudToDataConnectionMapper
import ru.zinoview.viewmodelmemoryleak.data.connection.ConnectionRepository
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module

class DataModule : Module {

    private val dataModule = module {
        single<ConnectionRepository<Unit>> {
            ConnectionRepository.Base(
                CloudToDataConnectionMapper(),
                get()
            )
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(dataModule)
    }
}