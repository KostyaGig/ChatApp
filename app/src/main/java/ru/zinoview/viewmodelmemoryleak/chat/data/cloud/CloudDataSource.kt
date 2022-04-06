package ru.zinoview.viewmodelmemoryleak.chat.data.cloud

import android.util.Log
import io.socket.client.Socket
import org.json.JSONObject
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.IdSharedPreferences
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface CloudDataSource : Disconnect {

    suspend fun emit()

    suspend fun model(block: (CloudModel) -> Unit)

    suspend fun modelFirst(block: (CloudModel) -> Unit)

    abstract class DisconnectSource(
        private val socket: Socket,
        private val connect: Connect
    ) : Disconnect  {

        override fun disconnect() = connect.disconnect(socket)
    }

    class Count(
        private val socket: Socket,
        private val connect: Connect
    ) : CloudDataSource, DisconnectSource(socket,connect) {

        override suspend fun emit() {
            try {
                connect.connect(socket)
                connect.disconnectBranch(socket,KEY_FIRST_VALUE)
                socket.emit(KEY_VALUE)
            } catch (e: Exception) {
                Log.d("zinoviewk","Count.emit(), ${e.javaClass}")
            }
        }

        override suspend fun model(block: (CloudModel) -> Unit) {
            data(KEY_VALUE,block)
        }

        override suspend fun modelFirst(block: (CloudModel) -> Unit) {
            data(KEY_FIRST_VALUE,block)
            socket.emit(KEY_FIRST_VALUE)
        }

        private fun data(keyConnection:String,block: (CloudModel) -> Unit) {
            try {
                connect.connect(socket)
                socket.on(keyConnection) { data ->
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

        private companion object {
            private const val KEY_VALUE = "counter"
            private const val KEY_FIRST_VALUE = "first_connection"
        }
    }

    interface Chat : Disconnect, CloudDataSource {

        fun joinUser(nickname: String)

        override suspend fun emit() = Unit
        override suspend fun model(block: (CloudModel) -> Unit) = Unit
        override suspend fun modelFirst(block: (CloudModel) -> Unit) = Unit

        class Base(
            private val socket: Socket,
            private val connect: Connect,
            private val idSharedPreferences: IdSharedPreferences
        ) : DisconnectSource(socket, connect), Chat {

            override fun joinUser(nickname: String) {
                connect.connect(socket)
                connect.addSocketBranch(JOIN_USER)

                // todo remove hardcode
                val user = JSONObject().apply {
                    put("nickname",nickname)
                }
                socket.on(JOIN_USER) { data ->
                    val id = data[0] as Int
                    idSharedPreferences.save(id)
                    Log.d("zinoviewk","saved id ${idSharedPreferences.read()}")
                }
                socket.emit(JOIN_USER,user)
            }

            private companion object {
                private const val JOIN_USER = "join_user"
            }
        }

    }

}