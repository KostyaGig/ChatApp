package ru.zinoview.viewmodelmemoryleak.chat.data.cloud

import android.util.Log
import io.socket.client.Socket
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface CloudDataSource {

    suspend fun emit()

    suspend fun model(block: (CloudModel) -> Unit)

    fun disconnect()
    class Count(
        private val socket: Socket,
        private val connect: Connect
    ) : CloudDataSource {

        override suspend fun emit() {
            try {
                connect.connect(socket)
                socket.emit(KEY_VALUE)
            } catch (e: Exception) {
                Log.d("zinoviewk","Count.emit(), ${e.javaClass}")
            }
        }

        override suspend fun model(block: (CloudModel) -> Unit) {
            return suspendCoroutine { continuation ->
                try {
                    connect.connect(socket)
                    socket.on(KEY_VALUE) { data ->
                        if (data != null) {
                            val count = data[0] as Int
                            block.invoke(CloudModel.Base(count))
                        } else {
                            block.invoke(CloudModel.Failure("received data is null"))
                        }
                    }
                } catch (e: Exception) {
                    block.invoke(CloudModel.Failure(e.message.toString()))
                }
            }
        }

        override fun disconnect() = connect.disconnect(socket)

        private companion object {
            private const val KEY_VALUE = "counter"
        }
    }
}