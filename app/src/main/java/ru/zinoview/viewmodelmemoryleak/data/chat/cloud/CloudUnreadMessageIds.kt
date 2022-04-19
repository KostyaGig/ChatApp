package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

interface CloudUnreadMessageIds {

    class Base(
        private val ids: List<String>
    ) : CloudUnreadMessageIds
}