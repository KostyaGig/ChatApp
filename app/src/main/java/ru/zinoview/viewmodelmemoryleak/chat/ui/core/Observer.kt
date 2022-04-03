package ru.zinoview.viewmodelmemoryleak.chat.ui.core


interface Observer<T> {

    fun update(data: T)

}