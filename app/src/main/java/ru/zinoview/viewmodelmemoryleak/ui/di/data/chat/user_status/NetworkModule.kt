package ru.zinoview.viewmodelmemoryleak.ui.di.data.chat.user_status

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.data.chat.user_status.FirebaseMessageNotification
import ru.zinoview.viewmodelmemoryleak.data.chat.user_status.cloud.CloudDataSource

class NetworkModule : ru.zinoview.viewmodelmemoryleak.ui.di.core.Module {

    private val networkModule = module {
        single<CloudDataSource> {
            CloudDataSource.Base(
                get(),
                FirebaseMessageNotification.Base(),
                get()
            )
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(networkModule)
    }
}