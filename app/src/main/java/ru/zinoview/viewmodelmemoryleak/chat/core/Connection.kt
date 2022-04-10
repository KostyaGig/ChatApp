package ru.zinoview.viewmodelmemoryleak.chat.core

interface Connection {

    fun <T> map(mapper: Mapper<T>) : T

    interface Mapper<T> {

        fun mapConnection() : T
        fun mapDisconnection(message: String) : T
        fun mapToolbarConnection(message: String) : T
    }
}