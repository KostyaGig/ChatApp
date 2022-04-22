package ru.zinoview.viewmodelmemoryleak.data.cache

import android.content.SharedPreferences

interface SharedPreferencesReader<T> {

    fun read(key: kotlin.String, prefs: SharedPreferences) : T

    class Id(
        private val id: ru.zinoview.viewmodelmemoryleak.data.cache.Id
    ) : SharedPreferencesReader<Int> {

        override fun read(key: kotlin.String, prefs: SharedPreferences) : Int
            = id.read(key, prefs)
    }

    class String : SharedPreferencesReader<kotlin.String> {

        // todo empty
        override fun read(key: kotlin.String, prefs: SharedPreferences)
            = prefs.getString(key,"") ?: ""
    }

}