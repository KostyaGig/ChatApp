package ru.zinoview.viewmodelmemoryleak.abstract_ex

import android.util.Log

interface Print {

    fun print(text: String)

    class Base : Print {

        override fun print(text: String) {
            Log.d(TAG,text)
        }

        private companion object {
            private const val TAG = "zinoviewk"
        }
    }
}