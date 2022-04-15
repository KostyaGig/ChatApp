package ru.zinoview.viewmodelmemoryleak.ui.chat.view

import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import ru.zinoview.viewmodelmemoryleak.ui.core.Show

interface SnackBar<T> : Show<T> {

    class Base(
        private val view: View,
        private val snackBarVisibility: SnackBarVisibility
    ) : SnackBar<String> {

        override fun show(message: String) {
            val snackBar = Snackbar.make(view,message,Snackbar.LENGTH_SHORT)
                .addCallback(snackBarVisibility)
            snackBar.show()
        }
    }

    class EmptyField(
        private val view: View,
        private val snackBarVisibility: SnackBarVisibility
    ) : SnackBar<Unit> {

        override fun show(arg: Unit) {
            val snackBar = Snackbar.make(view,MESSAGE,Snackbar.LENGTH_SHORT)
                .addCallback(snackBarVisibility)
            snackBar.show()
        }

        private companion object {
            private const val MESSAGE = "Enter empty field"
        }
    }

    open class SnackBarVisibility(
        private val container: LinearLayout? = null,
        private val snackBarHeight: SnackBarHeight
    ) : Snackbar.Callback () {

        override fun onShown(sb: Snackbar?) {
            val layoutParams = container?.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.bottomMargin = snackBarHeight.height()
            container.layoutParams = layoutParams
        }

        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
            val layoutParams = container?.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.bottomMargin = 0
            container.layoutParams = layoutParams
        }

        class Empty : SnackBarVisibility(
            snackBarHeight = SnackBarHeight.Empty
        ) {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) = Unit
            override fun onShown(sb: Snackbar?) = Unit
        }
    }
}