package ru.zinoview.viewmodelmemoryleak.chat.ui.chat.view

import ru.zinoview.viewmodelmemoryleak.chat.ui.core.ActionViewModel


interface FieldContentValid {

    fun check(message: String,viewModel: ActionViewModel<String>,field: MessageField)

    class Base(
        private val underLine: UnderLine
    ) : FieldContentValid {

        override fun check(message: String,viewModel: ActionViewModel<String>,field: MessageField) {
            if (message.isEmpty()) {
                underLine.changeColor(field)
            } else {
                viewModel.doAction(message)
            }
        }
    }
}