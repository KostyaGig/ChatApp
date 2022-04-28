package ru.zinoview.viewmodelmemoryleak.ui.di.data.chat.ui_state

import android.content.Context
import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.data.cache.SharedPreferencesReader
import ru.zinoview.viewmodelmemoryleak.data.chat.ui_state.UiStateRepository
import ru.zinoview.viewmodelmemoryleak.data.chat.ui_state.UiStateSharedPreferences
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module

class DataModule(
    private val context: Context
) : Module {

    private val dataModule = module {
        single<UiStateRepository> {
            UiStateRepository.Base(
                UiStateSharedPreferences.Base(
                    context,
                    SharedPreferencesReader.String(
                        get()
                    ),
                    get()
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