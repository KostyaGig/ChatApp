package ru.zinoview.viewmodelmemoryleak.data.core.cloud

import io.socket.client.Socket

interface ServerState {

   suspend fun serverState(socket: Socket) : CloudServerState
}