package ru.zinoview.viewmodelmemoryleak.chat.core.join


interface Join {
    fun <T> map(mapper: Mapper<T>) : T
}