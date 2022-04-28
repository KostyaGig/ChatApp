package ru.zinoview.viewmodelmemoryleak.ui.di.data.chat

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.core.IsNotEmpty
import ru.zinoview.viewmodelmemoryleak.core.Time
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.*

class NetworkModule : ru.zinoview.viewmodelmemoryleak.ui.di.core.Module {

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
        single {
            CloudDataSource.Base(
                get(),
                get(),
                get(),
                get(),
                Data.CloudMessage(),
                get(),
                get()
            )
        }

        single {
            CloudDataSource.Update(
                get(),get()
            )
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(networkModule)
    }
}