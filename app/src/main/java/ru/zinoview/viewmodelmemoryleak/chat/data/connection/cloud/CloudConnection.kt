package ru.zinoview.viewmodelmemoryleak.chat.data.connection.cloud

import ru.zinoview.viewmodelmemoryleak.chat.core.Connection

interface CloudConnection : Connection {

    object Success : CloudConnection {

        override fun <T> map(mapper: Connection.Mapper<T>): T
            = mapper.map()
    }

    open class Failure(
        private val message: String
    ) : CloudConnection {
        override fun <T> map(mapper: Connection.Mapper<T>): T
            = mapper.map(message)
    }

    class WaitingForServer : Failure(MESSAGE) {

        private companion object {
            private const val MESSAGE = "Waiting for server..."
        }
    }

}