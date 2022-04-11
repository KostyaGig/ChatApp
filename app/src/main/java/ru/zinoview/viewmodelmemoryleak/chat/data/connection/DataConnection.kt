package ru.zinoview.viewmodelmemoryleak.chat.data.connection

import ru.zinoview.viewmodelmemoryleak.chat.core.Connection

interface DataConnection : Connection {

    object Success : DataConnection {
        override fun <T> map(mapper: Connection.Mapper<T>): T
            = mapper.map()
    }

    class Failure(
        private val message: String
    ) : DataConnection {

        override fun <T> map(mapper: Connection.Mapper<T>): T
            = mapper.map(message)

    }

}