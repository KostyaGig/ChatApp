package ru.zinoview.viewmodelmemoryleak.core

interface Save<T> {

    fun save(data: T)
}