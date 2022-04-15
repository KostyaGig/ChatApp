package ru.zinoview.viewmodelmemoryleak.core.connection

interface Connection {

    fun <T> map(mapper: Mapper<T>) : T

    interface Mapper<T> {
        fun map() : T
        fun map(message: String) : T
    }
}