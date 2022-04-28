package ru.zinoview.viewmodelmemoryleak.core

interface Time<T> {

    fun time() : T

    class String : Time<kotlin.String> {

        override fun time() = System.currentTimeMillis().toString()
    }
}