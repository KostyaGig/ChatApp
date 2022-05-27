package ru.zinoview.viewmodelmemoryleak.ui.di.data.join

import android.content.Context
import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider
import ru.zinoview.viewmodelmemoryleak.data.join.cloud.StringIdToCloudIdMapper
import java.io.ByteArrayOutputStream

class NetworkModule : ru.zinoview.viewmodelmemoryleak.ui.di.core.Module {

    private val networkModule = module {
        single<ru.zinoview.viewmodelmemoryleak.data.join.cloud.CloudDataSource> {
            ru.zinoview.viewmodelmemoryleak.data.join.cloud.CloudDataSource.Base(
                get(),
                get(),
                get(),
                StringIdToCloudIdMapper.Base(get())
            )
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(networkModule)
    }
}