package ru.zinoview.viewmodelmemoryleak.ui.di.data.chat.processing_messages

import android.content.Context
import androidx.work.WorkManager
import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.data.cache.UserSharedPreferences
import ru.zinoview.viewmodelmemoryleak.data.chat.ChatAction
import ru.zinoview.viewmodelmemoryleak.data.chat.ChatRepository
import ru.zinoview.viewmodelmemoryleak.data.chat.workmanager.Worker
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudToDataMessageMapper
import ru.zinoview.viewmodelmemoryleak.data.chat.processing_messages.ProcessingChatMessagesRepository
import ru.zinoview.viewmodelmemoryleak.data.chat.update.ImmediatelyUpdateChatRepository
import ru.zinoview.viewmodelmemoryleak.data.chat.workmanager.WorkManagerWork
import ru.zinoview.viewmodelmemoryleak.data.chat.workmanager.WorkRequest
import ru.zinoview.viewmodelmemoryleak.data.connection.CloudToDataConnectionMapper
import ru.zinoview.viewmodelmemoryleak.data.connection.ConnectionRepository
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module

class DataModule : Module {

    private val dataModule = module {

        single<ProcessingChatMessagesRepository> {
            ProcessingChatMessagesRepository.Base(
                get(),
                get()
            )
        }

    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(dataModule)
    }
}