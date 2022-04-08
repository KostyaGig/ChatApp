package ru.zinoview.viewmodelmemoryleak.chat.data.cloud.chat

import com.google.gson.Gson
import io.socket.client.Socket
import ru.zinoview.viewmodelmemoryleak.chat.core.Observe
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.*

interface CloudDataSource : Disconnect<Unit>,Observe<List<CloudMessage>> {

    fun sendMessage(userId: Int,content: String)

    class Base(
        private val socket: Socket,
        private val connection: SocketConnection,
        private val json: Json,
        private val gson: Gson
    ) : ConnectionCloudDataSource.Base(socket, connection),CloudDataSource {

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

                block.invoke(messages)
            }
            connection.addSocketBranch(SEND_MESSAGE)
        }


    }

    private companion object {
        private const val SEND_MESSAGE = "send_message"

        private const val SENDER_ID_KEY = "senderId"
        private const val MESSAGE_CONTENT_KEY = "content"
    }

}