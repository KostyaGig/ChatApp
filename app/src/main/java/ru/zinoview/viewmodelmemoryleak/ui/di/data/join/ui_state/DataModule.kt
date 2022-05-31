package ru.zinoview.viewmodelmemoryleak.ui.di.data.join.ui_state

import android.content.Context
import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.data.cache.SharedPreferencesReader
import ru.zinoview.viewmodelmemoryleak.data.join.ui_state.JoinUiStateRepository
import ru.zinoview.viewmodelmemoryleak.data.join.ui_state.JoinUiStateSharedPreferences
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module

class DataModule(
    private val context: Context
) : Module {

    private val dataModule = module {
        single<JoinUiStateRepository> {
            JoinUiStateRepository.Base(
                JoinUiStateSharedPreferences.Base(
                    context,
                    SharedPreferencesReader.String(
                        get()
                    ),
                    get(),
                    get()
                )
            )
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(dataModule)
    }
}