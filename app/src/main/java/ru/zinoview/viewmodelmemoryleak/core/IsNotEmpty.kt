package ru.zinoview.viewmodelmemoryleak.core

interface IsNotEmpty<T> {

    fun isNotEmpty(arg: T) : Boolean

    class List<T> : IsNotEmpty<kotlin.collections.List<T>> {

        override fun isNotEmpty(arg: kotlin.collections.List<T>)
            = arg.isNotEmpty()
    }
}