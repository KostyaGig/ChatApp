package ru.zinoview.viewmodelmemoryleak.core.chat.ui_state

interface Messages<T> {

    fun messages() : List<T>
}