package ru.zinoview.viewmodelmemoryleak.data.core.ui_state

import android.content.Context
import com.google.gson.Gson
import ru.zinoview.viewmodelmemoryleak.core.Save
import ru.zinoview.viewmodelmemoryleak.data.cache.SharedPreferencesReader

interface UiStateSharedPreferences<T,R> : Save<T>, UiStateRead<T,R> {

    abstract class Base<T,R>(
        private val clazz: Class<T>,
        context: Context,
        private val gson: Gson,
        private val reader: SharedPreferencesReader<String>
    ) : UiStateSharedPreferences<T,R> {

        abstract fun key(): String
        abstract fun name(): String

        private val prefs by lazy {
            context.getSharedPreferences(key(), Context.MODE_PRIVATE)
        }

        override fun save(data: T) {
            val dataString = gson.toJson(data)
            prefs.edit().putString(key(), dataString).apply()
        }

        override fun read(key: Unit,map: (T) -> R ): R {
            val stringData = reader.read(key(), prefs)
            return map(gson.fromJson(stringData, clazz))
        }
    }
}

