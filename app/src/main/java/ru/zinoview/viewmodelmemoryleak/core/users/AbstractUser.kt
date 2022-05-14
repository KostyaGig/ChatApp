package ru.zinoview.viewmodelmemoryleak.core.users

import java.lang.IllegalStateException

interface AbstractUser {

    fun <T> map(mapper: UserMapper<T>) : T

    data class Base(
        private val id: String,
        private val nickName: String,
        private val lastMessageText: String,
        private val image: UserBitmap
    ) : AbstractUser {

        override fun <T> map(mapper: UserMapper<T>)
            = mapper.map(id,nickName, lastMessageText,image)
    }

    data class Test(
        private val id: String,
        private val nickName: String,
    ) : AbstractUser {
        override fun <T> map(mapper: UserMapper<T>) = throw IllegalStateException("AbstractUser.Test.map()")
    }
}