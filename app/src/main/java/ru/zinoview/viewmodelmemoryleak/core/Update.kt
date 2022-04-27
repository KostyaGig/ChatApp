package ru.zinoview.viewmodelmemoryleak.core

interface Update<T> {

    fun update(data: T)
}