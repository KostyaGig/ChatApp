package ru.zinoview.viewmodelmemoryleak.core.cloud

import io.socket.client.Socket


interface Push {

    fun push(socket: Socket,branch: String)
}