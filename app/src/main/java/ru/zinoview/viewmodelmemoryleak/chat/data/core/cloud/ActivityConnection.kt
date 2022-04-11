package ru.zinoview.viewmodelmemoryleak.chat.data.core.cloud

import io.socket.client.Socket
import kotlin.concurrent.thread

interface ActivityConnection {

    fun isNotActive(socket: Socket) : Boolean

    fun handle(socket: Socket, isNotActive: () -> Unit)

    class Base : ActivityConnection {

        override fun isNotActive(socket: Socket)
            = socket.isActive.not() || socket.id() == null

        override fun handle(socket: Socket, isNotActive: () -> Unit) {
            thread {
                // todo replace this approach for handle server life after the connection to the internet
                Thread.sleep(HANDLE_DELAY)
                if (isNotActive(socket)) {
                    isNotActive.invoke()
                }
            }
        }



        private companion object {
            private const val HANDLE_DELAY = 3000L
        }
    }
}