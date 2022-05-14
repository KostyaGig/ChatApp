package ru.zinoview.viewmodelmemoryleak.core.users

interface UserMapper<T> {

    fun map(userId: String, nickName: String, lastMessageText: String ,image: UserBitmap) : T

    interface Test {
        fun map(userId: String, nickName: String) : AbstractUser
    }
}