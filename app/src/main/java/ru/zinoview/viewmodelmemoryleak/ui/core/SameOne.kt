package ru.zinoview.viewmodelmemoryleak.ui.core

interface SameOne<T> {

    fun same(data: T) : Boolean
}