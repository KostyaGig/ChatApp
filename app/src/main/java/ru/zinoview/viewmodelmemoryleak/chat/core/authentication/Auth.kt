package ru.zinoview.viewmodelmemoryleak.chat.core.authentication

interface Auth {

    fun <T> map(mapper: Mapper<T>) : T
}