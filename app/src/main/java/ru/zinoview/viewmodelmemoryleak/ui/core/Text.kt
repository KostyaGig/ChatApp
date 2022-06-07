package ru.zinoview.viewmodelmemoryleak.ui.core

import android.text.Editable
import android.widget.EditText

interface Text {

    fun text(field: EditText) : String

    fun text(editable: Editable?) : String

    class Base : Text {
        override fun text(field: EditText)
            = field.text.toString().trim()

        override fun text(editable: Editable?) =  editable?.toString()?.trim() ?: ""

    }
}