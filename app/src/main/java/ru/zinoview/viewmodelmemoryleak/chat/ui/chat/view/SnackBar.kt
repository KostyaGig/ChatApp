package ru.zinoview.viewmodelmemoryleak.chat.ui.chat.view

import android.view.View
import com.google.android.material.snackbar.Snackbar

interface SnackBar {

    fun show(message: String)

    class Base(
        private val view: View
    ) : SnackBar {

        override fun show(message: String) {
            Snackbar.make(view,message,Snackbar.LENGTH_SHORT).show()
        }
    }
}