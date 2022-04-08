package ru.zinoview.viewmodelmemoryleak.chat.core.join

interface Mapper<T> {

    fun map() : T

    fun mapFailure() : T
}