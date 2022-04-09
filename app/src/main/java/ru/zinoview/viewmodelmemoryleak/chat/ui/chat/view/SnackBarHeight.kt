package ru.zinoview.viewmodelmemoryleak.chat.ui.chat.view

interface SnackBarHeight {

    fun height() : Int

    class Base : SnackBarHeight {

        override fun height() = DEFAULT

        private companion object {
            private const val DEFAULT = 80
        }
    }

    object Empty : SnackBarHeight {

        override fun height() = 0
    }
}