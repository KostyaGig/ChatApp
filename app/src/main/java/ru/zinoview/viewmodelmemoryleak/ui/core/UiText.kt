package ru.zinoview.viewmodelmemoryleak.ui.core

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.core.Mapper

interface UiText : Mapper<String,String>{


    class Base(
        private val maxLength: Int
    ) : UiText {

        override fun map(src: String): String {
            return if (src.length >= maxLength) {
                src.substring(START_INDEX,maxLength) + END_STRING
            } else {
                src
            }
        }

        private companion object {
            private const val START_INDEX = 0
            private const val END_STRING = "..."
        }
    }
}