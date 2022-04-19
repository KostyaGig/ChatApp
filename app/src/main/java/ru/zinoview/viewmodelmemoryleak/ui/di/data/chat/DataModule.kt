package ru.zinoview.viewmodelmemoryleak.ui.di.data.chat

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.data.chat.ChatRepository
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudToDataMessageMapper
import ru.zinoview.viewmodelmemoryleak.data.connection.CloudToDataConnectionMapper
import ru.zinoview.viewmodelmemoryleak.data.connection.ConnectionRepository
import ru.zinoview.viewmodelmemoryleak.data.connection.cloud.ConnectionState
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module

class DataModule : Module {

    private val dataModule = module {

        single<ChatRepository<Unit>> {
            ChatRepository.Base(
                get(),
                CloudToDataMessageMapper.Base(
                    get()
                ),
                get()
            )
        }

        factory<ConnectionRepository<Unit>> {
            ConnectionRepository.Base(
                CloudToDataConnectionMapper(),
                get()
            )
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(dataModule)
    }
}