package ru.zinoview.viewmodelmemoryleak.data.core.cloud

import android.util.Log
import io.socket.client.Socket
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface ActivityConnection : ServerState,ServerActivity {

    class Base(
        private val timer: Timer,
        private val serverActivity: ServerActivity
    ) : ActivityConnection {

        init {
            Log.d("zinoviewk","init ACTIVITY CONNECTION")
        }

        override suspend fun serverState(socket: Socket) : CloudServerState = suspendCoroutine {continuation ->
            timer.start(HANDLE_DELAY) {
                if (isNotActive(socket)) {
                    continuation.resume(
                        CloudServerState.Died
                    )
                } else {
                    continuation.resume(
                        CloudServerState.Alive
                    )
                    Log.d("zinoviewk","internet is ok server is ok")
                }
            }
        }

        override fun isNotActive(socket: Socket)
            = serverActivity.isNotActive(socket)

        private companion object {
            private const val HANDLE_DELAY = 3000L
        }
    }
}