package ru.zinoview.viewmodelmemoryleak.ui.di.ui.connection

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.ui.connection.ConnectionCommunication
import ru.zinoview.viewmodelmemoryleak.ui.connection.DataToUiConnectionMapper
import ru.zinoview.viewmodelmemoryleak.ui.connection.NetworkConnectionWork
import ru.zinoview.viewmodelmemoryleak.ui.connection.UiConnectionWrapper

class UiModule : ru.zinoview.viewmodelmemoryleak.ui.di.core.Module {
    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(uiModule)
    }

    private val uiModule = module {
        factory<UiConnectionWrapper> {
            UiConnectionWrapper.Base(
                get(),
                NetworkConnectionWork.Base(
                    get(),
                    DataToUiConnectionMapper()
                ),
                get(),
                ConnectionCommunication(),
                DataToUiConnectionMapper()
            )
        }
    }
}