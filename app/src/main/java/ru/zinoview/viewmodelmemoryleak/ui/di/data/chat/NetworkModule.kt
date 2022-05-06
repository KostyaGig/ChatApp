package ru.zinoview.viewmodelmemoryleak.ui.di.data.chat

import android.content.Context
import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.core.IsNotEmpty
import ru.zinoview.viewmodelmemoryleak.core.Time
import ru.zinoview.viewmodelmemoryleak.core.cloud.SocketWrapper
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.*
import ru.zinoview.viewmodelmemoryleak.ui.chat.notification.NotificationService

class NetworkModule(
    context: Context
) : ru.zinoview.viewmodelmemoryleak.ui.di.core.Module {

    private val networkModule = module {
        single<MessagesStore> {
            MessagesStore.Base(
                ListItem.Base(),
                ToProgressEditMessageMapper(
                    Time.String()
                ),
                IsNotEmpty.List(),
                ListSize.Base(),
                get()
            )
        }
        single<CloudDataSource<Unit>> {
            CloudDataSource.Base(
                get(),
                get(),
                Data.CloudMessage(),
                get(),
                get(),
                NotificationService.Push(context)
            )
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(networkModule)
    }
}