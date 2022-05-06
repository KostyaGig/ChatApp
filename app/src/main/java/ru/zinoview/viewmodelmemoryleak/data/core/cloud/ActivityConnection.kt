package ru.zinoview.viewmodelmemoryleak.data.core.cloud

import ru.zinoview.viewmodelmemoryleak.core.cloud.SocketWrapper
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface ActivityConnection : ServerState {

    class Base(
        private val timer: Timer
    ) : ActivityConnection {

        override suspend fun serverState(socket: SocketWrapper) : CloudServerState = suspendCoroutine {continuation ->
            timer.start(HANDLE_DELAY) {
                if (socket.isNotActive()) {
                    continuation.resume(
                        CloudServerState.Died
                    )
                } else {
                    continuation.resume(
                        CloudServerState.Alive
                    )
                }
            }
        }

        private companion object {
            private const val HANDLE_DELAY = 3000L
        }
    }
}