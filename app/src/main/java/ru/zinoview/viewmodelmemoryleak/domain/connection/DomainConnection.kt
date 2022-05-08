package ru.zinoview.viewmodelmemoryleak.domain.connection

import ru.zinoview.viewmodelmemoryleak.core.connection.Connection

interface DomainConnection : Connection {

    class Success(
        private val message: String
    ) : DomainConnection {

        override fun <T> map(mapper: Connection.Mapper<T>): T
            = mapper.mapSuccess(message)
    }

    data class Message(
        private val message: String
    ) : DomainConnection {

        override fun <T> map(mapper: Connection.Mapper<T>): T
            = mapper.mapMessage(message)

    }

    object Empty : DomainConnection {
        override fun <T> map(mapper: Connection.Mapper<T>): T
            = mapper.mapMessage("")
    }


}