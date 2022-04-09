package ru.zinoview.viewmodelmemoryleak.chat.data.cloud

import io.socket.client.Socket

interface ConnectionCloudDataSource : Disconnect<Unit> {

    abstract class Base(
        private val socket: Socket,
        private val connect: SocketConnection
    ) : Disconnect<Unit>  {

        override fun disconnect(arg: Unit) = connect.disconnect(socket)
    }
}