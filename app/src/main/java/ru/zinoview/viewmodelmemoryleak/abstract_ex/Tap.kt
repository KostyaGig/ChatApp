package ru.zinoview.viewmodelmemoryleak.abstract_ex

interface Tap {

    fun tap()

    fun print()

    class Base(
        private val print: Print
    ) : Tap {

        private var count = 0

        override fun tap() {
            count++
        }

        override fun print() = print.print("Tap count $count")
    }

    object Empty : Tap {
        override fun tap() = Unit

        override fun print() = Unit
    }
}