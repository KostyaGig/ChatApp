package ru.zinoview.viewmodelmemoryleak.core.authentication

interface Mapper<T> {

    fun map() : T

    fun mapFailure() : T
}