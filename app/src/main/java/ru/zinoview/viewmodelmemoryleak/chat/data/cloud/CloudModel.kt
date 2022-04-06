package ru.zinoview.viewmodelmemoryleak.chat.data.cloud

import android.widget.TextView

interface CloudModel {

    fun show(textView: TextView)

    data class Base<T>(private val data: T) : CloudModel {

        override fun show(textView: TextView) {
            textView.text = data.toString()
        }
    }

    data class Failure(
        private val message: String
    ) : CloudModel {

        override fun show(textView: TextView) {
            textView.text = message
        }
    }
}