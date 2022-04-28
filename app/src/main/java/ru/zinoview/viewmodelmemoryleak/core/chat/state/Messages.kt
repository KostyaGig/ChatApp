package ru.zinoview.viewmodelmemoryleak.core.chat.state

interface Messages<T> {

    fun messages() : List<T>
}