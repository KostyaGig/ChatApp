package ru.zinoview.viewmodelmemoryleak.core.chat

interface UpdateMessagesState {

    fun updateMessagesState(range: Pair<Int,Int>)
}