package ru.zinoview.viewmodelmemoryleak.chat.data.cache

import android.content.SharedPreferences

interface Id : SharedPreferencesReader<Int> {

    fun isEmpty(src: Int) : Boolean

    class Base : Id {

        override fun isEmpty(src: Int) = src == EMPTY_ID

        override fun read(key: String,prefs: SharedPreferences)
            = prefs.getInt(key,EMPTY_ID)

        private companion object {
            private const val EMPTY_ID = -1
        }
    }
}