package ru.zinoview.viewmodelmemoryleak.ui.di.data.join

import org.koin.dsl.module.module

class NetworkModule : ru.zinoview.viewmodelmemoryleak.ui.di.core.Module {

    private val networkModule = module {
        single<ru.zinoview.viewmodelmemoryleak.data.join.cloud.CloudDataSource> {
            ru.zinoview.viewmodelmemoryleak.data.join.cloud.CloudDataSource.Base(
                get(),
                get(),
            )
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(networkModule)
    }
}