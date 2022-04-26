package ru.zinoview.viewmodelmemoryleak.ui.di.data.connection

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.core.IsNotEmpty
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.Filter
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.ProcessingMessages
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.ProcessingMessagesAdd
import ru.zinoview.viewmodelmemoryleak.data.connection.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.connection.cloud.ConnectionState

class NetworkModule : ru.zinoview.viewmodelmemoryleak.ui.di.core.Module {
    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(networkModule)
    }
    private val networkModule = module {

        single<ProcessingMessages> {
            ProcessingMessages.Base(
                IsNotEmpty.List(),
                ProcessingMessagesAdd.Base(),
                Filter.ProcessingMessages(
                    IsNotEmpty.List()
                ),
                get()
            )
        }

        single<CloudDataSource<Unit>> {
            CloudDataSource.Base(
                get(), get(), ConnectionState.Base(
                    get(),get(),get()
                )
            )
        }
    }


}