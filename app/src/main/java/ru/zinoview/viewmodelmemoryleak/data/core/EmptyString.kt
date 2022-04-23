package ru.zinoview.viewmodelmemoryleak.data.core

interface EmptyString {

    fun string(src: String?) : String

    class Base : EmptyString {

        override fun string(src: String?) = src ?: DEFAULT

        private companion object {
            private const val DEFAULT = ""
        }

    }
}