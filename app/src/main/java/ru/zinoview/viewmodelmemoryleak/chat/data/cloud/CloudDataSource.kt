package ru.zinoview.viewmodelmemoryleak.chat.data.cloud

import android.app.UiModeManager
import android.util.Log
import com.google.gson.Gson
import io.socket.client.Socket
import org.json.JSONObject
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.chat.ui.UiChatMessage

interface CloudDataSource : Disconnect {

    abstract class DisconnectSource(
        private val socket: Socket,
        private val connect: Connect
    ) : Disconnect  {

        override fun disconnect() = connect.disconnect(socket)
    }

    interface JoinUser : Disconnect, CloudDataSource {

        fun joinUser(nickname: String,block: () -> Unit)

        class Base(
            private val socket: Socket,
            private val connect: Connect,
            private val idSharedPreferences: IdSharedPreferences
        ) : DisconnectSource(socket, connect), JoinUser {

            override fun joinUser(nickname: String,block: () -> Unit) {
                connect.connect(socket)
                connect.addSocketBranch(JOIN_USER)

                // todo remove hardcode
                val user = JSONObject().apply {
                    put("nickname",nickname)
                }
                socket.on(JOIN_USER) { data ->
                    val id = data[0] as Int
                    idSharedPreferences.save(id)
                    block.invoke()
                }
                socket.emit(JOIN_USER,user)
            }

            private companion object {
                private const val JOIN_USER = "join_user"
            }
        }
    }

    interface SendMessage : Disconnect{

        fun sendMessage(content: String)

        fun observeMessages(block:(List<UiChatMessage>) -> Unit)

        class Base(
            private val socket: Socket,
            private val connect: Connect,
            private val idSharedPreferences: IdSharedPreferences
        ) : SendMessage, DisconnectSource(socket, connect) {

            override fun sendMessage(content: String) {
                val id = idSharedPreferences.read()

                // todo remove hardcode
                val message = JSONObject().apply {
                    put("senderId",id)
                    put("content",content)
                }

                connect.connect(socket)
                socket.emit(SEND_MESSAGE,message)
            }


            interface Mapper<T> {

                fun map() : T
            }

            data class CloudMessage(
                private val id: String,
                private val senderId: Int,
                private val content: String,
                private val senderNickname: String
            ) {
                fun map(idPrefs: IdSharedPreferences) : UiChatMessage = if (idPrefs.read() == senderId) {
                        UiChatMessage.Sent(id,content, senderId.toString(), senderNickname)
                    } else {
                        UiChatMessage.Received(id,content, senderId.toString(), senderNickname)
                    }
                }

            data class Value(
                private val nameValuePairs: CloudMessage
            ) : Mapper<CloudMessage> {

                override fun map(): CloudMessage
                    = nameValuePairs
            }

            data class Root (
                private val values: ArrayList<Value>
            ) : Mapper<List<CloudMessage>> {
                override fun map(): List<CloudMessage>
                    = values.map { value ->
                        value.map()
                    }
            }

            override fun observeMessages(block: (List<UiChatMessage>) -> Unit) {
                connect.connect(socket)
                socket.on(SEND_MESSAGE) { data ->
                    val json = Gson().toJson(data.get(0))
                    val messages = Gson().fromJson(json,Root::class.java).map()
                    val uiMessages = messages.map { it.map(idSharedPreferences) }
                    block.invoke(uiMessages)

                    Log.d("zinoviewk","observe messages $uiMessages")
                }
                connect.addSocketBranch(SEND_MESSAGE)
            }



        }

        private companion object {
            private const val SEND_MESSAGE = "send_message"
        }
    }

}