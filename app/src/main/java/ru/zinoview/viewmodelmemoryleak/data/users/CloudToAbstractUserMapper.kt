package ru.zinoview.viewmodelmemoryleak.data.users

import android.graphics.Bitmap
import ru.zinoview.viewmodelmemoryleak.core.users.AbstractUser
import ru.zinoview.viewmodelmemoryleak.core.users.UserMapper

interface CloudToAbstractUserMapper : UserMapper<AbstractUser> {

    class Base : CloudToAbstractUserMapper {
        override fun map(userId: String, nickName: String, lastMessageText: String, image: Bitmap)
            = AbstractUser.Base(userId,nickName, lastMessageText,image)
    }
}