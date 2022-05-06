package ru.zinoview.viewmodelmemoryleak.data.core.cloud

import io.socket.client.Socket

interface ServerActivity {

    fun isNotActive(socket: Socket) : Boolean

    class Base : ServerActivity {

         override fun isNotActive(socket: Socket): Boolean {
             val isActive = socket.isActive
             val idServer = socket.id()
             return isActive.not() || idServer == null
         }
    }
}