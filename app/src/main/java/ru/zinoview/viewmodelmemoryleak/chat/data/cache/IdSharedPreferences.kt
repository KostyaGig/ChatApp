package ru.zinoview.viewmodelmemoryleak.chat.data.cache

import android.content.Context

interface IdSharedPreferences {

    fun save(id: Int)

    fun read() : Int

    class Base(
        private val reader: SharedPreferencesReader<Int>,
        context: Context
    ) : IdSharedPreferences {

        private val prefs = context.getSharedPreferences(NAME,Context.MODE_PRIVATE)

        override fun read(): Int = reader.read(KEY,prefs)

        override fun save(id: Int) = prefs.edit().putInt(KEY,id).apply()


        private companion object {
            private const val NAME = "user_id_prefs"
            private const val KEY = "user_id"
        }
    }
}