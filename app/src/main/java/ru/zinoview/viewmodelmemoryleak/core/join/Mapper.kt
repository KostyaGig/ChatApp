package ru.zinoview.viewmodelmemoryleak.core.join

interface Mapper<T> {

    fun map() : T

    fun map(message: String) : T
}