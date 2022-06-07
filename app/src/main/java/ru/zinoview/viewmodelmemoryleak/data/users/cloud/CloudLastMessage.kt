package ru.zinoview.viewmodelmemoryleak.data.users.cloud

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.core.Mapper
import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider

interface CloudLastMessage : Mapper<ResourceProvider,String> {

    class Base(
        private val userNickName: String,
        private val lastMessage: String?,
        private val lastMessageSenderNickName: String?
    ) : CloudLastMessage {

        override fun map(resourceProvider: ResourceProvider): String {
            Log.d("zinoviewk","sender name $userNickName, msg $lastMessage")
            return when {
                lastMessage == null -> resourceProvider.string(R.string.start_chatting_text)
                userNickName == lastMessageSenderNickName -> {
                    resourceProvider.string(R.string.you_prefix) + lastMessage
                }
                else -> "$lastMessageSenderNickName : $lastMessage"
            }
        }
    }
}