package ru.zinoview.viewmodelmemoryleak.chat.data.cloud

interface ActivityConnection {

    fun isNotActive(socket: io.socket.client.Socket) : Boolean

    class Base : ActivityConnection {

        override fun isNotActive(socket: io.socket.client.Socket)
            = socket.isActive.not()
    }
}