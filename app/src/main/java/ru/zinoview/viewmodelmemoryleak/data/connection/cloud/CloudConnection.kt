package ru.zinoview.viewmodelmemoryleak.data.connection.cloud

import ru.zinoview.viewmodelmemoryleak.core.connection.Connection

interface CloudConnection : Connection {

    class Success(
        private val message: String
    ) : CloudConnection {

        override fun <T> map(mapper: Connection.Mapper<T>): T
            = mapper.mapMessage(message)
    }

    class Message(
        private val message: String
    ) : CloudConnection {

        override fun <T> map(mapper: Connection.Mapper<T>): T
            = mapper.mapMessage(message)
    }

}