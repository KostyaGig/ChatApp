package ru.zinoview.viewmodelmemoryleak.ui.join.ui_state

import android.text.Editable
import android.text.TextWatcher

interface UiTextStateTextWatcher : TextWatcher {

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
    class Base(
        private val viewModel: JoinUiStateViewModel,
        private val text: ru.zinoview.viewmodelmemoryleak.ui.core.Text
    ): UiTextStateTextWatcher {

        override fun afterTextChanged(p0: Editable?) {
            val text = text.text(p0)
            if (text.isNotEmpty()) {
                viewModel.save(JoinUiStates.Base(JoinUiState.UserNickName(text)))
            }
        }
    }

}