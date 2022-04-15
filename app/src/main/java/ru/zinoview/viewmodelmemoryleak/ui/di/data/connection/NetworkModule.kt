package ru.zinoview.viewmodelmemoryleak.ui.di.data.connection

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.data.connection.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.connection.cloud.ConnectionState

class NetworkModule : ru.zinoview.viewmodelmemoryleak.ui.di.core.Module {
    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(networkModule)
    }
    private val networkModule = module {
        single<CloudDataSource> {
            CloudDataSource.Base(
                get(), get(), ConnectionState.Base(
                    get(),get(),get()
                ),get()
            )
        }
    }


}