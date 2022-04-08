package ru.zinoview.viewmodelmemoryleak.chat.core

interface Observe<T> {

    fun observe(block:(T) -> Unit)
}