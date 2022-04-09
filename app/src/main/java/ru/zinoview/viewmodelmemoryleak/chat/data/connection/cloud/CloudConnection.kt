package ru.zinoview.viewmodelmemoryleak.chat.data.connection.cloud

import ru.zinoview.viewmodelmemoryleak.chat.core.Connection

interface CloudConnection : Connection {

    object Connect : CloudConnection {

        override fun <T> map(mapper: Connection.Mapper<T>): T
            = mapper.mapConnection()
    }

    abstract class BaseDisconnect : CloudConnection {

        protected companion object {
            const val MESSAGE = "Waiting for server..."
        }
    }

    object Disconnect : BaseDisconnect() {
        override fun <T> map(mapper: Connection.Mapper<T>): T
            = mapper.mapDisconnection(MESSAGE)
    }

    object ToolBarDisconnect : BaseDisconnect() {
        override fun <T> map(mapper: Connection.Mapper<T>): T
            = mapper.mapToolbarDisconnection(MESSAGE)
    }
}