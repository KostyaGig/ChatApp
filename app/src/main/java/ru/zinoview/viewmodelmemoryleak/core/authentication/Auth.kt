package ru.zinoview.viewmodelmemoryleak.core.authentication

interface Auth {

    fun <T> map(mapper: Mapper<T>) : T
}