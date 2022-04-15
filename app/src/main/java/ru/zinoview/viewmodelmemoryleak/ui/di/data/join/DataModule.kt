package ru.zinoview.viewmodelmemoryleak.ui.di.data.join

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.data.chat.ChatRepository
import ru.zinoview.viewmodelmemoryleak.data.chat.CloudToDataMessageMapper
import ru.zinoview.viewmodelmemoryleak.data.connection.CloudToDataConnectionMapper
import ru.zinoview.viewmodelmemoryleak.data.connection.ConnectionRepository
import ru.zinoview.viewmodelmemoryleak.data.connection.cloud.ConnectionState
import ru.zinoview.viewmodelmemoryleak.data.core.ExceptionMapper
import ru.zinoview.viewmodelmemoryleak.data.join.JoinUserRepository
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module

class DataModule : Module {

    private val dataModule = module {

        single<JoinUserRepository> {
            JoinUserRepository.Base(
                get(),
                get(),
                get()
            )
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(dataModule)
    }
}