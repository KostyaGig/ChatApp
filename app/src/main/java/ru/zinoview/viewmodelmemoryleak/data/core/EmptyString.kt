package ru.zinoview.viewmodelmemoryleak.data.core

interface EmptyString {

    fun string(src: String?,default: String = "") : String

    class Base : EmptyString {

        override fun string(src: String?,default: String) = src ?: default
    }
}