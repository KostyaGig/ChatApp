package ru.zinoview.viewmodelmemoryleak.chat.ui.chat.view


import android.content.res.ColorStateList
import android.graphics.Color


interface UnderLine {

    fun changeColor(field: MessageField)

    class Base : UnderLine {

        override fun changeColor(field: MessageField) {
            val colorStateList = ColorStateList.valueOf(
                Color.RED
            )
            field.showFailure(colorStateList)
        }

    }
}