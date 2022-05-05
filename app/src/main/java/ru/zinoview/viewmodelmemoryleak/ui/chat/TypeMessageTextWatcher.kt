package ru.zinoview.viewmodelmemoryleak.ui.chat

import android.text.Editable
import android.text.TextWatcher
import kotlinx.coroutines.CoroutineScope
import ru.zinoview.viewmodelmemoryleak.ui.core.Dispatcher
import ru.zinoview.viewmodelmemoryleak.ui.core.DoBackground

interface TypeMessageTextWatcher : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

    class Base(
        private val timer: TypeMessageTimer,
        private val dispatcher: Dispatcher,
        private val scope: CoroutineScope
    ) : TypeMessageTextWatcher {

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = timer.startTyping()

        override fun afterTextChanged(p0: Editable?) = dispatcher.doBackground(scope) {
            timer.stopTyping()
        }
    }
}
