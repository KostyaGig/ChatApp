package ru.zinoview.viewmodelmemoryleak.ui.di.data.chat.notification

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.data.chat.notification.NotificationMessageRepository
import ru.zinoview.viewmodelmemoryleak.data.chat.notification.cloud.CloudToDataMessageNotificationMapper
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module

class DataModule : Module {

    private val dataModule = module {
        single<NotificationMessageRepository> {
            NotificationMessageRepository.Base(get(),CloudToDataMessageNotificationMapper.Base())
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(dataModule)
    }
}