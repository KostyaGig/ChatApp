package ru.zinoview.viewmodelmemoryleak.core.join


interface Join {
    fun <T> map(mapper: Mapper<T>) : T
}