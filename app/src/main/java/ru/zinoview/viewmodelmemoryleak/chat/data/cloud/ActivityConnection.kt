package ru.zinoview.viewmodelmemoryleak.chat.data.cloud

import io.socket.client.Socket
import kotlinx.coroutines.delay

interface ActivityConnection {

    fun isNotActive(socket: Socket) : Boolean

    suspend fun handle(socket: Socket, isNotActive: () -> Unit)

    class Base : ActivityConnection {

        override fun isNotActive(socket: Socket)
            = socket.isActive.not() || socket.id() == null

        override suspend fun handle(socket: Socket, isNotActive: () -> Unit) {
            delay(HANDLE_DELAY)
            if (isNotActive(socket)) {
                isNotActive.invoke()
            }
        }

        private companion object {
            private const val HANDLE_DELAY = 5000L
        }
    }
}