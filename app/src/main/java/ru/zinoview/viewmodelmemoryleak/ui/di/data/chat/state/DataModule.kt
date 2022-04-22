package ru.zinoview.viewmodelmemoryleak.ui.di.data.chat.state

import android.content.Context
import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.data.cache.SharedPreferencesReader
import ru.zinoview.viewmodelmemoryleak.data.chat.state.UiStateRepository
import ru.zinoview.viewmodelmemoryleak.data.chat.state.UiStateSharedPreferences
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module

class DataModule(
    private val context: Context
) : Module {

    private val dataModule = module {
        single<UiStateRepository> {
            UiStateRepository.Base(
                UiStateSharedPreferences.Base(
                    context,
                    SharedPreferencesReader.String(),
                    get()
                )
            )
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(dataModule)
    }
}