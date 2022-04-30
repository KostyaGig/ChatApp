package ru.zinoview.viewmodelmemoryleak.ui.di.core

import android.content.Context
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider
import ru.zinoview.viewmodelmemoryleak.core.Time

class CoreModule(
    private val context: Context
) : ru.zinoview.viewmodelmemoryleak.ui.di.core.Module {
    override fun add(modules: MutableList<Module>) {
        modules.add(coreModule)
    }

    private val coreModule = module {
        single<ResourceProvider> {
            ResourceProvider.Base(
                context
            )
        }

        single<Time<String>> {
            Time.String()
        }

    }
}