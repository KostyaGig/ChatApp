package ru.zinoview.viewmodelmemoryleak.core

interface Mapper<S,R> {

    fun map(src: S) : R
}