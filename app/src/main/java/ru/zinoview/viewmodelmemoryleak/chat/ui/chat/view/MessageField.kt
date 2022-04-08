package ru.zinoview.viewmodelmemoryleak.chat.ui.chat.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Action
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.ActionViewModel

interface MessageField : Action<ActionViewModel<String>> {

        fun showFailure(colorStateList: ColorStateList)

        class Base @JvmOverloads constructor(
            context: Context,
            attrs: AttributeSet? = null,
            defStyle: Int = 0
        ) : androidx.appcompat.widget.AppCompatEditText(context, attrs, defStyle), MessageField {

            private val messageValid = FieldContentValid.Base(
                UnderLine.Base()
            )


            override fun doAction(viewModel: ActionViewModel<String>) {
                messageValid.check(text.toString().trim(),viewModel,this)
                setText("")
            }

            @SuppressLint("RestrictedApi")
            override fun showFailure(colorStateList: ColorStateList) {
                supportBackgroundTintList = colorStateList
            }
        }
}