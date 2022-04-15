package ru.zinoview.viewmodelmemoryleak.data.cache

import android.content.SharedPreferences

interface SharedPreferencesReader<T> {

    fun read(key: String,prefs: SharedPreferences) : T

    class Base(
        private val id: ru.zinoview.viewmodelmemoryleak.data.cache.Id
    ) : ru.zinoview.viewmodelmemoryleak.data.cache.SharedPreferencesReader<Int> {

        override fun read(key: String, prefs: SharedPreferences) : Int
            = id.read(key, prefs)
    }
}