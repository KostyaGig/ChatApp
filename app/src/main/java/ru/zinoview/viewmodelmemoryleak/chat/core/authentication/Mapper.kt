package ru.zinoview.viewmodelmemoryleak.chat.core.authentication

interface Mapper<T> {

    fun map() : T

    fun mapFailure() : T
}