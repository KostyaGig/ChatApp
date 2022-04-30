package ru.zinoview.viewmodelmemoryleak.ui.di.data.chat.update

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.data.chat.update.cloud.CloudDataSource

class NetworkModule : ru.zinoview.viewmodelmemoryleak.ui.di.core.Module {

    private val networkModule = module {
        single<CloudDataSource> {
            CloudDataSource.Base(
                get(),
                get()
            )
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(networkModule)
    }
}