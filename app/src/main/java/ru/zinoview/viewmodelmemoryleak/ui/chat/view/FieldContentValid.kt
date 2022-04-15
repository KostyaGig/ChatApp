package ru.zinoview.viewmodelmemoryleak.ui.chat.view

import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider
import ru.zinoview.viewmodelmemoryleak.ui.core.ActionViewModel


interface FieldContentValid {

    fun check(message: String, viewModel: ActionViewModel<String>, field: MessageField)

    class Base(
        private val snackBar: SnackBar<String>,
        private val resourceProvider: ResourceProvider
    ) : FieldContentValid {

        override fun check(message: String,viewModel: ActionViewModel<String>,field: MessageField) {
            if (message.isEmpty()) {
                snackBar.show(
                    resourceProvider.string(R.string.field_is_empty_text)
                )
            } else {
                viewModel.doAction(message)
            }
        }
    }
}