package ru.zinoview.viewmodelmemoryleak.core.cloud

import io.socket.client.Socket

interface SocketData : Push {

    class Base(
        private val obj: Any
    ) : SocketData {

        override fun push(socket: Socket, branch: String) {
            socket.emit(branch,obj)
        }
    }

    object Empty : SocketData{
        override fun push(socket: Socket, branch: String) {
            socket.emit(branch)
        }
    }
}