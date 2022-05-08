package ru.zinoview.viewmodelmemoryleak.ui.core

import android.widget.EditText

interface Text {

    fun text(field: EditText) : String

    class Base : Text {
        override fun text(field: EditText)
            = field.text.toString().trim()
    }
}