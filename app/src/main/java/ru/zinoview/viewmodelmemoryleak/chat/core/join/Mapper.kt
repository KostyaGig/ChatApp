package ru.zinoview.viewmodelmemoryleak.chat.core.join

interface Mapper<T> {

    fun map() : T

    fun map(message: String) : T
}