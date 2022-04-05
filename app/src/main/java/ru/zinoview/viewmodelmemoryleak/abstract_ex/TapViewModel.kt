package ru.zinoview.viewmodelmemoryleak.abstract_ex

import androidx.lifecycle.ViewModel

interface TapViewModel {

    fun tap() = Unit

    fun printTapCount() = Unit

    class Base(
        private val tap: Tap
    ) : TapViewModel, ViewModel() {

        override fun tap() = tap.tap()

        override fun printTapCount() = tap.print()
    }

}