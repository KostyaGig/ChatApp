package ru.zinoview.viewmodelmemoryleak.chat.data.cache

import android.content.SharedPreferences

interface SharedPreferencesReader<T> {

    fun read(key: String,prefs: SharedPreferences) : T

    class Base : SharedPreferencesReader<Int> {

        override fun read(key: String, prefs: SharedPreferences) : Int
            = prefs.getInt(key,EMPTY_ID)

        private companion object {
            private const val EMPTY_ID = -1
        }
    }
}