package ru.zinoview.viewmodelmemoryleak.data.core.cloud

import io.socket.client.Socket
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface ActivityConnection : ServerState,ServerActivity {

    class Base(
        private val timer: Timer,
        private val serverActivity: ServerActivity
    ) : ActivityConnection {

        override suspend fun serverState(socket: Socket) : CloudServerState = suspendCoroutine {continuation ->
            timer.start(HANDLE_DELAY) {
                if (serverActivity.isNotActive(socket)) {
                    continuation.resume(
                        CloudServerState.Died
                    )
                }
            }
        }

        override fun isNotActive(socket: Socket) = serverActivity.isNotActive(socket)

        private companion object {
            private const val HANDLE_DELAY = 3000L
        }
    }
}