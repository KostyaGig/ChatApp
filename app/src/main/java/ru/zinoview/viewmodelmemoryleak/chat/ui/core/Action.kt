package ru.zinoview.viewmodelmemoryleak.chat.ui.core

interface Action<T> {

    fun doAction(arg: T)
}