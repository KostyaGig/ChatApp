package ru.zinoview.viewmodelmemoryleak.ui.di.data.cache

import android.content.Context
import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.data.cache.*
import ru.zinoview.viewmodelmemoryleak.data.core.EmptyString


class CoreCacheModule(
    private val context: Context
) : ru.zinoview.viewmodelmemoryleak.ui.di.core.Module {
    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(cacheModule)
    }

    private val cacheModule = module {

        single<UserSharedPreferences> {
            UserSharedPreferences.Base(
                get(),get()
            )
        }

        single<IdSharedPreferences<String,Unit>> {
            IdSharedPreferences.Base(
                SharedPreferencesReader.String(
                    get()
                ),
                get(),
                context
            )
        }

        single<NickNameSharedPreferences<String,Unit>> {
            NickNameSharedPreferences.Base(
                SharedPreferencesReader.String(
                    get()
                ),
                context
            )
        }

        single<Id> {
            Id.Base(get())
        }

        single<EmptyString> {
            EmptyString.Base()
        }

    }
}