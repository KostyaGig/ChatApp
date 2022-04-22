package ru.zinoview.viewmodelmemoryleak.core

interface Read<T,K> {

    fun read(key: K) : T
}