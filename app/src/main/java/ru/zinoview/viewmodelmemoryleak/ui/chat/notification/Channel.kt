package ru.zinoview.viewmodelmemoryleak.ui.chat.notification


import android.os.Build

import androidx.annotation.RequiresApi


interface Channel : CreateChannel {

    fun notification(iconRes: Int, content: String,id: String,time: String) : NotificationWrapper

    class Base(
        private val groupId: GroupId,
        private val uniqueNotification: UniqueNotification
    ) : Channel {

        @RequiresApi(Build.VERSION_CODES.O)
        override fun createChannel() = groupId.createChannel()

        override fun notification(iconRes: Int, content: String,id: String,time: String)
            = uniqueNotification.notification(iconRes,content,id,time)
    }
}