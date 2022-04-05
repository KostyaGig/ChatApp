package ru.zinoview.viewmodelmemoryleak.chat.data.cloud

import android.util.Log
import io.socket.client.Socket


interface Connect {

    fun connect(socket: Socket)

    fun disconnect(socket: Socket)

    class Base : Connect {

        override fun connect(socket: Socket) {
            if (socket.isActive.not()) {
                socket.connect()
            } else {
                Log.d("zinoviewk","socket is connect")
            }
        }

        override fun disconnect(socket: Socket) {
            if (socket.isActive) {
                socket.disconnect()
            }
        }
    }
}