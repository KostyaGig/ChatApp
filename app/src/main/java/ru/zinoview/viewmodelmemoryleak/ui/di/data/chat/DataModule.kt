package ru.zinoview.viewmodelmemoryleak.ui.di.data.chat

import android.content.Context
import androidx.work.WorkManager
import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.data.chat.ChatAction
import ru.zinoview.viewmodelmemoryleak.data.chat.ChatRepository
import ru.zinoview.viewmodelmemoryleak.data.chat.Worker
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudToDataMessageMapper
import ru.zinoview.viewmodelmemoryleak.data.connection.CloudToDataConnectionMapper
import ru.zinoview.viewmodelmemoryleak.data.connection.ConnectionRepository
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module

class DataModule(
    private val context: Context
) : Module {

    private val dataModule = module {

        single<ChatRepository<Unit>> {
            val workManager = WorkManager.getInstance(context)
            ChatRepository.Base(
                get(),
                get(),
                CloudToDataMessageMapper.Base(
                    get()
                ),
                get(),
                Worker.Chat(
                    workManager
                ),
                ChatAction.SendMessage(),
                ChatAction.EditMessage()
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