package ru.zinoview.viewmodelmemoryleak.ui.di.domain.chat

import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.domain.chat.ChatInteractor
import ru.zinoview.viewmodelmemoryleak.domain.chat.DataToDomainMessageMapper
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module

class DomainModule : Module {

    private val domainModule = module {
        single<ChatInteractor> {
            ChatInteractor.Base(
                get(),
                get(),
                get(),
                DataToDomainMessageMapper.Base()
            )
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(domainModule)
    }
}