package ru.zinoview.viewmodelmemoryleak.ui.di.data.chat.update

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.data.chat.update.ImmediatelyUpdateChatRepository
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module

class DataModule : Module {

    private val dataModule = module {

        single<ImmediatelyUpdateChatRepository> {
            ImmediatelyUpdateChatRepository.Base(
                get(),
                get()
            )
        }

    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(dataModule)
    }
}