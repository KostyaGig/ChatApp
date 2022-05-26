package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

interface UnreadMessageIds<S> {

    fun unreadMessageIds(src: S) : List<String>
}