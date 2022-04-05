package ru.zinoview.viewmodelmemoryleak.chat.data.cloud

interface Count {

    fun emitValue(key: String,socket: io.socket.client.Socket)

    fun inc()

    class Base : Count {

        private var count = 0

        override fun emitValue(key: String,socket: io.socket.client.Socket) {
            socket.emit(key,count)
        }

        override fun inc() {
            count++
        }

    }
}