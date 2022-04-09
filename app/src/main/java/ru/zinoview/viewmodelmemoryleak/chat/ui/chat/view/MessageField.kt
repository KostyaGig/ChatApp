package ru.zinoview.viewmodelmemoryleak.chat.ui.chat.view

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Action
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.ActionViewModel

interface MessageField : Action<ActionViewModel<String>> {

        class Base @JvmOverloads constructor(
            context: Context,
            attrs: AttributeSet? = null,
            defStyle: Int = 0
        ) : EditText(context, attrs, defStyle), MessageField {

            private val messageValid = FieldContentValid.Base(
                SnackBar.Base(this,SnackBar.SnackBarVisibility.Empty())
            )


            override fun doAction(viewModel: ActionViewModel<String>) {
                messageValid.check(text.toString().trim(),viewModel,this)
                setText("")
            }

        }
}