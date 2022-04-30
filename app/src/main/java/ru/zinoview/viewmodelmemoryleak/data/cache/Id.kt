package ru.zinoview.viewmodelmemoryleak.data.cache

import android.content.SharedPreferences
import ru.zinoview.viewmodelmemoryleak.core.IsEmpty
import ru.zinoview.viewmodelmemoryleak.data.core.EmptyString

interface Id : SharedPreferencesReader<String>, IsEmpty<String> {

    class Base(
        private val emptyString: EmptyString
    ) : Id {

        override fun isEmpty(src: String) = src == EMPTY_ID

        override fun read(key: String,prefs: SharedPreferences)
            = emptyString.string(prefs.getString(key, EMPTY_ID
            ), EMPTY_ID)

        private companion object {
            private const val EMPTY_ID = "-1"
        }
    }
}