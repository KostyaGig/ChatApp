package ru.zinoview.viewmodelmemoryleak.ui.core.navigation

import android.content.Context
import ru.zinoview.viewmodelmemoryleak.data.cache.SharedPreferencesReader

interface FragmentSharedPreferences {

    fun save(value: String)

    fun read() : String

    class Base(
        private val context: Context,
        private val reader: SharedPreferencesReader<String>
    ) : FragmentSharedPreferences {

        private val prefs = context.getSharedPreferences(NAME,Context.MODE_PRIVATE)

        override fun read() = reader.read(KEY,prefs)

        override fun save(value: String) {
            prefs.edit().putString(KEY,value).apply()
        }
        private companion object {
            private const val NAME = "fragments_prefs"
            private const val KEY = "fragment"
        }
    }
}