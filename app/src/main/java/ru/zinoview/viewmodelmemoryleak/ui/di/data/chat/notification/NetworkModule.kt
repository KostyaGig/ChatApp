package ru.zinoview.viewmodelmemoryleak.ui.di.data.chat.notification

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.core.IsNotEmpty
import ru.zinoview.viewmodelmemoryleak.core.Time
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.*
import ru.zinoview.viewmodelmemoryleak.data.chat.notification.cloud.CloudDataSource

class NetworkModule : ru.zinoview.viewmodelmemoryleak.ui.di.core.Module {

    private val networkModule = module {
        single<CloudDataSource> {
            CloudDataSource.Base(get(),get(),get())
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(networkModule)
    }
}