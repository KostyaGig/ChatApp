package ru.zinoview.viewmodelmemoryleak.ui.di.ui.connection

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.ui.connection.ConnectionCommunication
import ru.zinoview.viewmodelmemoryleak.ui.connection.ConnectionViewModel
import ru.zinoview.viewmodelmemoryleak.ui.connection.DataToUiConnectionMapper
import ru.zinoview.viewmodelmemoryleak.ui.connection.NetworkConnectionWork
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module


class UiModule : Module {

    private val uiConnectionModule = module {
        scope(SCOPE_NAME) {
            val mapper = DataToUiConnectionMapper.Base()
            ConnectionViewModel.Base(
                get(),
                NetworkConnectionWork.Base(get(), mapper),
                get(),
                ConnectionCommunication(),
                mapper
            )
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
         modules.add(uiConnectionModule)
    }

    private companion object {
        const val SCOPE_NAME = "cvfScope"
    }
}