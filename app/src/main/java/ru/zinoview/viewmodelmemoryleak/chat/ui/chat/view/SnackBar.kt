package ru.zinoview.viewmodelmemoryleak.chat.ui.chat.view

import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar

interface SnackBar {

    fun show(message: String)

    class Base(
        private val view: View,
        private val snackBarVisibility: SnackBarVisibility
    ) : SnackBar {

        override fun show(message: String) {
            val snackBar = Snackbar.make(view,message,Snackbar.LENGTH_SHORT)
                .addCallback(snackBarVisibility)
            snackBar.show()
        }
    }

    open class SnackBarVisibility(
        private val container: LinearLayout? = null,
        private val snackBarHeight: SnackBarHeight
    ) : Snackbar.Callback () {

        override fun onShown(sb: Snackbar?) {
            Log.d("zinoviewk","onShown own")
            val layoutParams = container?.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.bottomMargin = snackBarHeight.height()
            container.layoutParams = layoutParams
        }

        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
            Log.d("zinoviewk","onDissmised")

            val layoutParams = container?.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.bottomMargin = 0
            container.layoutParams = layoutParams
        }

        class Empty : SnackBarVisibility(
            snackBarHeight = SnackBarHeight.Empty
        )
    }
}