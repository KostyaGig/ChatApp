package ru.zinoview.viewmodelmemoryleak.data.connection.cloud

import ru.zinoview.viewmodelmemoryleak.core.connection.Connection

interface CloudConnection : Connection {

    object Success : CloudConnection {

        override fun <T> map(mapper: Connection.Mapper<T>): T
            = mapper.map()
    }

    class Failure(
        private val message: String
    ) : CloudConnection {
        override fun <T> map(mapper: Connection.Mapper<T>): T
            = mapper.map(message)
    }

}