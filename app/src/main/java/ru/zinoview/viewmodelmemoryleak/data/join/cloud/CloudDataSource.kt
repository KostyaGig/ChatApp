package ru.zinoview.viewmodelmemoryleak.data.join.cloud

import io.socket.client.Socket
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.AbstractCloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Disconnect
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Json
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.SocketConnection
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface CloudDataSource : Disconnect<Unit>, AbstractCloudDataSource {

    suspend fun joinedUserId(nickname: String) : String

    class Base(
        private val socket: Socket,
        private val connection: SocketConnection,
        private val json: Json,
    ) : AbstractCloudDataSource.Base(socket, connection), CloudDataSource {

        override suspend fun joinedUserId(nickname: String) : String {
            connection.connect(socket)
            return suspendCoroutine { continuation ->
                connection.addSocketBranch(JOIN_USER)
                val user = json.json(
                    Pair(
                        NICKNAME_KEY,
                        nickname
                    )
                )

                socket.on(JOIN_USER) { data ->
                    val id = data.first() as Int
                    continuation.resume(id.toString())
                }
                socket.emit(JOIN_USER,user)
            }
        }

        private companion object {
            private const val JOIN_USER = "join_user"

            private const val NICKNAME_KEY = "nickname"
        }
    }
}