package ru.zinoview.viewmodelmemoryleak.data.cache

import android.content.SharedPreferences
import ru.zinoview.viewmodelmemoryleak.data.core.EmptyString

interface SharedPreferencesReader<T> {

    fun read(key: kotlin.String, prefs: SharedPreferences) : T

    class Id(
        private val id: ru.zinoview.viewmodelmemoryleak.data.cache.Id
    ) : SharedPreferencesReader<Int> {

        override fun read(key: kotlin.String, prefs: SharedPreferences) : Int
            = id.read(key, prefs)
    }

    class String(
        private val emptyString: EmptyString
    ) : SharedPreferencesReader<kotlin.String> {

        override fun read(key: kotlin.String, prefs: SharedPreferences)
            = emptyString.string(prefs.getString(key,""))
    }

}