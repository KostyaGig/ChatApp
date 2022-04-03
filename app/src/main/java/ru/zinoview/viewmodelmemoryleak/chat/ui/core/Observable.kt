package ru.zinoview.viewmodelmemoryleak.chat.ui.core

interface Observable<T> {

    fun observe(observer: Observer<T>)

    fun add(message: T)
}