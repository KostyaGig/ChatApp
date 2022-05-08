package ru.zinoview.viewmodelmemoryleak.core.connection

interface Connection {

    fun <T> map(mapper: Mapper<T>) : T

    interface Mapper<T> {
        fun mapSuccess(message: String) : T
        fun mapMessage(message: String) : T
    }
}