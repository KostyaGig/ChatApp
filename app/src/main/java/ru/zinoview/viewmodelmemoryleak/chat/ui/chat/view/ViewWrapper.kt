package ru.zinoview.viewmodelmemoryleak.chat.ui.chat.view

import android.view.View
import android.widget.EditText
import android.widget.TextView
import ru.zinoview.viewmodelmemoryleak.chat.data.core.cloud.Disconnect
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Show
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.ShowFew

interface ViewWrapper : Disconnect<Unit>, Show<Unit> ,ShowFew<Unit,String> {

    override fun show(arg: Unit) = Unit
    override fun show(arg: Unit, arg2: String) = Unit
    override fun disconnect(arg: Unit) = Unit

    class Base(
        private val view: View
    ) : ViewWrapper {

        override fun disconnect(arg: Unit) {
            view.visibility = View.GONE
        }

        override fun show(arg: Unit) {
            view.visibility = View.VISIBLE
        }
    }

    class Text(
        private val textView: TextView
    ) : ViewWrapper {

        override fun show(arg: Unit, text: String) {
            textView.text = text
        }

    }

    class EditText(
        private val editText: android.widget.EditText
    ) : ViewWrapper {

        override fun disconnect(arg: Unit) {
            editText.setText("")
        }
    }

    object Empty : ViewWrapper
}