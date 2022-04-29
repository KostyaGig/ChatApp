package ru.zinoview.viewmodelmemoryleak.data.cache

import android.content.Context
import ru.zinoview.viewmodelmemoryleak.core.IsEmpty
import ru.zinoview.viewmodelmemoryleak.core.Read
import ru.zinoview.viewmodelmemoryleak.core.Save

interface NickNameSharedPreferences<T,K> : IsEmpty<Unit>, Save<T>, Read<T, K> {

    class Base(
        private val reader: SharedPreferencesReader<String>,
        context: Context
    ) : NickNameSharedPreferences<String,Unit> {

        private val prefs = context.getSharedPreferences(NAME,Context.MODE_PRIVATE)

        override fun read(arg: Unit): String = reader.read(KEY,prefs)

        override fun save(name: String)
            = prefs.edit().putString(KEY,name).apply()

        override fun isEmpty(arg: Unit) = read(Unit).isEmpty()

        private companion object {
            private const val NAME = "user_nick_name_prefs"
            private const val KEY = "user_nick_name"
        }
    }
}