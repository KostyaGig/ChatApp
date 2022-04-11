package ru.zinoview.viewmodelmemoryleak.chat.ui.chat.view

import ru.zinoview.viewmodelmemoryleak.chat.ui.core.ActionViewModel


interface FieldContentValid {

    fun check(message: String,viewModel: ActionViewModel<String>,field: MessageField)

    class Base(
        private val snackBar: SnackBar<String>
    ) : FieldContentValid {

        override fun check(message: String,viewModel: ActionViewModel<String>,field: MessageField) {
            if (message.isEmpty()) {
                snackBar.show(
                    EMPTY_FIELD_MESSAGE
                )
            } else {
                viewModel.doAction(message)
            }
        }

        private companion object {
            private const val EMPTY_FIELD_MESSAGE = "The field is empty"
        }
    }
}