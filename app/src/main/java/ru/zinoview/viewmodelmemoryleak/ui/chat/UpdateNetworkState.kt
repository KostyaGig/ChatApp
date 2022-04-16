package ru.zinoview.viewmodelmemoryleak.ui.chat

interface UpdateNetworkState<T> {

    fun updateNetworkState(isConnected: Boolean,arg: T)
}