package ru.zinoview.viewmodelmemoryleak.core.cloud

interface Emit {

    fun emit(branch: String,data: SocketData)
}