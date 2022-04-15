package ru.zinoview.viewmodelmemoryleak.core.chat

interface Message {

    fun <T> map(mapper: Mapper<T>) : T
}