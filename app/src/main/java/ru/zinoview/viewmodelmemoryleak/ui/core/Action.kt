package ru.zinoview.viewmodelmemoryleak.ui.core

interface Action<T> {

    fun doAction(arg: T)
}