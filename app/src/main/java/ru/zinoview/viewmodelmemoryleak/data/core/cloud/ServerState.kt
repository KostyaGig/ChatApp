package ru.zinoview.viewmodelmemoryleak.data.core.cloud

import ru.zinoview.viewmodelmemoryleak.core.cloud.SocketWrapper

interface ServerState {

   suspend fun serverState(socket: SocketWrapper) : CloudServerState
}