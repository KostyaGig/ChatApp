package ru.zinoview.viewmodelmemoryleak.data.cache

import android.content.Context
import ru.zinoview.viewmodelmemoryleak.core.IsEmpty
import ru.zinoview.viewmodelmemoryleak.core.Read
import ru.zinoview.viewmodelmemoryleak.core.Save

interface IdSharedPreferences<T,K> : IsEmpty<Unit>, Save<T>, Read<T,K> {

    class Base(
        private val id: Id,
        context: Context
    ) : IdSharedPreferences<String,Unit> {

        private val prefs = context.getSharedPreferences(NAME,Context.MODE_PRIVATE)

        override fun read(arg: Unit) = id.read(KEY,prefs)

        override fun save(id: String)
            = prefs.edit().putString(KEY,id).apply()

        override fun isEmpty(arg: Unit) = id.isEmpty(read(Unit))

        private companion object {
            private const val NAME = "user_id_prefs"
            private const val KEY = "user_id"
        }
    }
}