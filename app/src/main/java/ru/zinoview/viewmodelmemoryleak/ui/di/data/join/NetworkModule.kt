package ru.zinoview.viewmodelmemoryleak.ui.di.data.join

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.Data
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.MessagesStore

class NetworkModule : ru.zinoview.viewmodelmemoryleak.ui.di.core.Module {

    private val networkModule = module {
        single<ru.zinoview.viewmodelmemoryleak.data.join.cloud.CloudDataSource> {
            ru.zinoview.viewmodelmemoryleak.data.join.cloud.CloudDataSource.Base(
                get(),
                get(),
                get()
            )
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(networkModule)
    }
}