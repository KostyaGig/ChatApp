package ru.zinoview.viewmodelmemoryleak.data.connection

import ru.zinoview.viewmodelmemoryleak.core.connection.Connection

interface DataConnection : Connection {

    object Success : DataConnection {
        override fun <T> map(mapper: Connection.Mapper<T>): T
            = mapper.map()
    }

    data class Failure(
        private val message: String
    ) : DataConnection {

        override fun <T> map(mapper: Connection.Mapper<T>): T
            = mapper.map(message)

    }

}