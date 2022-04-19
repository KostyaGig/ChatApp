package ru.zinoview.viewmodelmemoryleak.ui.core

interface Same<T,E> {

    fun same(arg1: T,arg2: E) : Boolean
}