package ru.zinoview.viewmodelmemoryleak.ui.di.ui.core

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.ui.core.Dispatcher
import ru.zinoview.viewmodelmemoryleak.ui.core.Text

class CoreUiModule : ru.zinoview.viewmodelmemoryleak.ui.di.core.Module {

    private val coreUiModule = module {

        single<Dispatcher> {
            Dispatcher.Base()
        }

        single<Text> {
            Text.Base()
        }

    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(coreUiModule)
    }
}