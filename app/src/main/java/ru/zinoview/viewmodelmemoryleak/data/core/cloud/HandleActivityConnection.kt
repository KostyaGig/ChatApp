package ru.zinoview.viewmodelmemoryleak.data.core.cloud

import io.socket.client.Socket

interface HandleActivityConnection {

    fun handleActivityConnection(socket: Socket, isNotActive: () -> Unit)
}