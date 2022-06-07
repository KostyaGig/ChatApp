package ru.zinoview.viewmodelmemoryleak.data.users.cloud

import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider
import ru.zinoview.viewmodelmemoryleak.core.users.AbstractUser
import ru.zinoview.viewmodelmemoryleak.core.users.UserBitmap

interface CloudMessageMapper<T> {

    fun map(
        id: String,
        nickName: String,
        cloudLastMessage: CloudLastMessage,
        bitmap: android.graphics.Bitmap
    ) : T

    class Base(
        private val resourceProvider: ResourceProvider
    ) : CloudMessageMapper<AbstractUser> {

        override fun map(
            id: String,
            nickName: String,
            cloudLastMessage: CloudLastMessage,
            bitmap: android.graphics.Bitmap
        ) = AbstractUser.Base(id,nickName,cloudLastMessage.map(resourceProvider),UserBitmap.Base(bitmap))

    }
}