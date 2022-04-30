package ru.zinoview.viewmodelmemoryleak.ui.di.data.chat

import android.content.Context
import androidx.work.WorkManager
import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.data.chat.ChatAction
import ru.zinoview.viewmodelmemoryleak.data.chat.ChatActions
import ru.zinoview.viewmodelmemoryleak.data.chat.ChatRepository
import ru.zinoview.viewmodelmemoryleak.data.chat.workmanager.Worker
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudToDataMessageMapper
import ru.zinoview.viewmodelmemoryleak.data.chat.workmanager.WorkManagerWork
import ru.zinoview.viewmodelmemoryleak.data.chat.workmanager.WorkRequest
import ru.zinoview.viewmodelmemoryleak.data.connection.CloudToDataConnectionMapper
import ru.zinoview.viewmodelmemoryleak.data.connection.ConnectionRepository
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module

class DataModule(
    private val context: Context
) : Module {

    private val dataModule = module {

        single<ChatRepository<Unit>> {
            val workManager = WorkManager.getInstance(context)
            val worker = Worker.Chat(
                WorkRequest.Network.Send(),
                WorkRequest.Network.Edit(),
                WorkManagerWork.Send(workManager),
                WorkManagerWork.Edit(workManager)
            )
            ChatRepository.Base(
                get(),
                get(),
                get(),
                ChatActions.Base(
                    worker,ChatAction.SendMessage(
                        get()
                    ),
                    ChatAction.EditMessage(get())
                )
            )
        }

        factory<ConnectionRepository<Unit>> {
            ConnectionRepository.Base(
                CloudToDataConnectionMapper(),
                get()
            )
        }

        single<CloudToDataMessageMapper> {
            CloudToDataMessageMapper.Base(
                get()
            )
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(dataModule)
    }
}