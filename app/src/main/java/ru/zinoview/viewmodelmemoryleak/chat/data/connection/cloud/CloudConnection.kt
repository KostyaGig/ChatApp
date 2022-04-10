package ru.zinoview.viewmodelmemoryleak.chat.data.connection.cloud

import ru.zinoview.viewmodelmemoryleak.chat.core.Connection

interface CloudConnection : Connection {

    object Connect : CloudConnection {

        override fun <T> map(mapper: Connection.Mapper<T>): T
            = mapper.mapConnection()
    }


    class Disconnect(
        private val message: String
    ) : CloudConnection {
        override fun <T> map(mapper: Connection.Mapper<T>): T
            = mapper.mapDisconnection(message)
    }

    class ToolBarConnection(
        private val message: String
    ) : CloudConnection {
        override fun <T> map(mapper: Connection.Mapper<T>): T
            = mapper.mapToolbarConnection(message)
    }

}