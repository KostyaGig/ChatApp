package ru.zinoview.viewmodelmemoryleak.chat.ui.chat.view

import android.content.Context
import android.util.AttributeSet
import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.ChatViewModel

interface MessageField {

        fun send(viewModel: ChatViewModel)

        class Base @JvmOverloads constructor(
            context: Context,
            attrs: AttributeSet? = null,
            defStyle: Int = 0
        ) : androidx.appcompat.widget.AppCompatEditText(context, attrs, defStyle), MessageField {

            override fun send(viewModel: ChatViewModel) {
                viewModel.sendMessage(text.toString().trim())
                setText("")
            }
        }
}