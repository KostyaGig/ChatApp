package ru.zinoview.viewmodelmemoryleak.chat.data.connection

import ru.zinoview.viewmodelmemoryleak.chat.core.Connection

interface DataConnection : Connection {

    object Connection : DataConnection {
        override fun <T> map(mapper: ru.zinoview.viewmodelmemoryleak.chat.core.Connection.Mapper<T>): T
            = mapper.mapConnection()
    }

    class Disconnection(
        private val message: String
    ) : DataConnection {

        override fun <T> map(mapper: ru.zinoview.viewmodelmemoryleak.chat.core.Connection.Mapper<T>): T
            = mapper.mapDisconnection(message)

    }

    class ToolbarConnection(
        private val message: String
    ) : DataConnection {

        override fun <T> map(mapper: ru.zinoview.viewmodelmemoryleak.chat.core.Connection.Mapper<T>): T
            = mapper.mapToolbarConnection(message)

    }
}