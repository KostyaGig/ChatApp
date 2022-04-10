package ru.zinoview.viewmodelmemoryleak.chat.data.chat.cloud

import android.util.Log
import com.google.gson.Gson
import io.socket.client.Socket
import ru.zinoview.viewmodelmemoryleak.chat.core.Observe
import ru.zinoview.viewmodelmemoryleak.chat.data.core.cloud.AbstractCloudDataSource
import ru.zinoview.viewmodelmemoryleak.chat.data.core.cloud.Disconnect
import ru.zinoview.viewmodelmemoryleak.chat.data.core.cloud.Json
import ru.zinoview.viewmodelmemoryleak.chat.data.core.cloud.SocketConnection

interface CloudDataSource : Disconnect<Unit>,Observe<List<CloudMessage>> {

    fun sendMessage(userId: Int,content: String)

    suspend fun messages(block:(List<CloudMessage>) -> Unit)

    class Base(
        private val socket: Socket,
        private val connection: SocketConnection,
        private val json: Json,
        private val gson: Gson,
        private val data: Data<List<CloudMessage>>
    ) : AbstractCloudDataSource.Base(socket, connection), CloudDataSource {

        override fun sendMessage(userId: Int,content: String) {
            val message = json.create(
                Pair(
                    SENDER_ID_KEY,userId
                ),
                Pair(
                    MESSAGE_CONTENT_KEY,content
                )
            )

            connection.connect(socket)
            socket.emit(SEND_MESSAGE,message)

        }


        override fun observe(block: (List<CloudMessage>) -> Unit) {
            connection.connect(socket)
            socket.on(SEND_MESSAGE) { data ->

                val wrapperMessages = gson.toJson(data.first())
                val messages = gson.fromJson(wrapperMessages, WrapperMessages::class.java).map()

                Log.d("zinoviewk","observe() messages $messages")

                block.invoke(messages)
            }
            connection.addSocketBranch(SEND_MESSAGE)
        }

        override suspend fun messages(block:(List<CloudMessage>) -> Unit) {

            connection.connect(socket)
            socket.on(MESSAGES) { cloudData ->
                val wrapperMessages = gson.toJson(cloudData.first())
                val modelMessages = gson.fromJson(wrapperMessages, WrapperMessages::class.java).map()

                val messages = data.data(modelMessages)

                Log.d("zinoviewk","messages() messages $messages")

                block.invoke(messages)

                connection.disconnectBranch(socket, MESSAGES)
            }
            socket.emit(MESSAGES)
            connection.addSocketBranch(MESSAGES)

            observe(block)
        }

    }

    private companion object {
        private const val SEND_MESSAGE = "send_message"
        private const val MESSAGES = "messages"

        private const val SENDER_ID_KEY = "senderId"
        private const val MESSAGE_CONTENT_KEY = "content"
    }

}