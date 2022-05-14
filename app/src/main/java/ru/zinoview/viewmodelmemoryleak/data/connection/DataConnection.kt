package ru.zinoview.viewmodelmemoryleak.data.connection

import ru.zinoview.viewmodelmemoryleak.core.connection.Connection

interface DataConnection : Connection {

    data class Success(
        private val message: String
    ) : DataConnection {

        override fun <T> map(mapper: Connection.Mapper<T>): T
            = mapper.mapSuccess(message)
    }

    data class Message(
        private val message: String
    ) : DataConnection {

        override fun <T> map(mapper: Connection.Mapper<T>): T
            = mapper.mapMessage(message)

    }


}