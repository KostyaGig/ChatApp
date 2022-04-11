package ru.zinoview.viewmodelmemoryleak.chat.data.core.cloud

import io.socket.client.Socket

interface HandleActivityConnection {

    fun handleActivityConnection(socket: Socket, isNotActive: () -> Unit)
}