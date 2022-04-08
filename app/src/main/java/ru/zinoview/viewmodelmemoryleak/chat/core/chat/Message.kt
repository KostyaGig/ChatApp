package ru.zinoview.viewmodelmemoryleak.chat.core.chat

interface Message {

    fun <T> map(mapper: Mapper<T>) : T
}