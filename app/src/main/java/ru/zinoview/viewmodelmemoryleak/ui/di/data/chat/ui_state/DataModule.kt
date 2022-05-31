package ru.zinoview.viewmodelmemoryleak.ui.di.data.chat.ui_state

import android.content.Context
import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.data.cache.SharedPreferencesReader
import ru.zinoview.viewmodelmemoryleak.data.chat.ui_state.ChatUiStateRepository
import ru.zinoview.viewmodelmemoryleak.data.chat.ui_state.ChatUiStateSharedPreferences
import ru.zinoview.viewmodelmemoryleak.data.core.ui_state.UiStateRepository
import ru.zinoview.viewmodelmemoryleak.data.core.ui_state.UiStateSharedPreferences
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module

class DataModule(
    private val context: Context
) : Module {

    private val dataModule = module {
        single<ChatUiStateRepository> {
            ChatUiStateRepository.Base(
                ChatUiStateSharedPreferences.Base(
                    context,
                    get(),
                    SharedPreferencesReader.String(
                        get()
                    ),
                ),
                get(),
                get()
            )
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(dataModule)
    }
}