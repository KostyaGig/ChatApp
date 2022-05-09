package ru.zinoview.viewmodelmemoryleak.core.users

import android.graphics.Bitmap
import ru.zinoview.viewmodelmemoryleak.data.users.cloud.CloudUser

interface UserMapper<T> {

    fun map(userId: String, nickName: String, image: Bitmap) : T

    interface Test {
        fun map(userId: String, nickName: String) : AbstractUser
    }
}