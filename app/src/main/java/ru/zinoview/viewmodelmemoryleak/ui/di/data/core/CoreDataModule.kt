package ru.zinoview.viewmodelmemoryleak.ui.di.data.core

import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.data.core.ExceptionMapper

class CoreDataModule : ru.zinoview.viewmodelmemoryleak.ui.di.core.Module {
    override fun add(modules: MutableList<Module>) {
        modules.add(coreDataModule)
    }

    private val coreDataModule = module {

        single<ExceptionMapper> {
            ExceptionMapper.Base(
                get()
            )
        }
    }
}