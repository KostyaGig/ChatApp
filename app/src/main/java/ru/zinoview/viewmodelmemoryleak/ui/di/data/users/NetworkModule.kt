package ru.zinoview.viewmodelmemoryleak.ui.di.data.users

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.data.users.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.users.cloud.UpdateUser
import ru.zinoview.viewmodelmemoryleak.data.users.cloud.UpdateUsers

class NetworkModule : ru.zinoview.viewmodelmemoryleak.ui.di.core.Module {

    private val networkModule = module {
        single<CloudDataSource> {
            CloudDataSource.Base(
                get(),
                get(),
                UpdateUsers.Base(UpdateUser.Base())
            )
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(networkModule)
    }
}