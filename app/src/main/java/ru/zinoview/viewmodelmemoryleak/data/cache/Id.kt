package ru.zinoview.viewmodelmemoryleak.data.cache

import android.content.SharedPreferences
import ru.zinoview.viewmodelmemoryleak.core.IsEmpty

interface Id : SharedPreferencesReader<Int>, IsEmpty<Int> {

    class Base : Id {

        override fun isEmpty(src: Int) = src == EMPTY_ID

        override fun read(key: String,prefs: SharedPreferences)
            = prefs.getInt(key,
            EMPTY_ID
        )

        private companion object {
            private const val EMPTY_ID = -1
        }
    }
}