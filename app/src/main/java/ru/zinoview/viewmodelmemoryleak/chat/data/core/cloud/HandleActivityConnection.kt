package ru.zinoview.viewmodelmemoryleak.chat.data.core.cloud

import io.socket.client.Socket

interface HandleActivityConnection {

    suspend fun handleActivityConnection(socket: Socket, isNotActive: () -> Unit)
}