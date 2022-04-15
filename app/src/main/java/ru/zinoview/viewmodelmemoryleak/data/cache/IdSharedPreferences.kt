package ru.zinoview.viewmodelmemoryleak.data.cache

import android.content.Context
import ru.zinoview.viewmodelmemoryleak.core.IsEmpty

interface IdSharedPreferences : IsEmpty<Unit>{

    fun save(id: Int)

    fun read() : Int

    class Base(
        private val reader: SharedPreferencesReader<Int>,
        private val id: Id,
        context: Context
    ) : IdSharedPreferences {

        private val prefs = context.getSharedPreferences(NAME,Context.MODE_PRIVATE)

        override fun read(): Int = reader.read(KEY,prefs)

        override fun save(id: Int) {
            prefs.edit().putInt(KEY,id).apply()
        }

        override fun isEmpty(arg: Unit) = id.isEmpty(read())

        private companion object {
            private const val NAME = "user_id_prefs"
            private const val KEY = "user_id"
        }
    }
}