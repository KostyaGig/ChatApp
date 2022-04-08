package ru.zinoview.viewmodelmemoryleak.chat.data.cloud.join

import io.socket.client.Socket
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.ConnectionCloudDataSource
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.Disconnect
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.Json
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.SocketConnection

interface CloudDataSource : Disconnect<Unit>, ConnectionCloudDataSource {

    fun join(nickname: String,block: (Int) -> Unit)

    class Base(
        private val socket: Socket,
        private val connection: SocketConnection,
        private val json: Json,
    ) : ConnectionCloudDataSource.Base(socket, connection), CloudDataSource {

        override fun join(nickname: String, block: (Int) -> Unit) {
            connection.connect(socket)
            connection.addSocketBranch(JOIN_USER)

            val user = json.create(
                Pair(
                    NICKNAME_KEY,
                    nickname
                )
            )

            socket.on(JOIN_USER) { data ->
                val id = data.first() as Int
                block.invoke(id)
            }
            socket.emit(JOIN_USER,user)
        }

        private companion object {
            private const val JOIN_USER = "join_user"

            private const val NICKNAME_KEY = "nickname"
        }

    }
}