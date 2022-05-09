package ru.zinoview.viewmodelmemoryleak.data.users.cloud

import ru.zinoview.viewmodelmemoryleak.core.users.AbstractUser
import ru.zinoview.viewmodelmemoryleak.core.users.UserMapper
import ru.zinoview.viewmodelmemoryleak.ui.join.Bitmap
import java.lang.IllegalStateException

interface CloudUser {

    fun <T> map(mapper: UserMapper<T>,bitmap: Bitmap) : T

    open class Base(
        private val id: String,
        private val nickName: String,
        private val image: String
    ) : CloudUser {

        object Empty : Base("","","")

        override fun <T> map(mapper: UserMapper<T>,bitmap: Bitmap) : T {
            val bitmapImage = bitmap.bitmap(image)
            return mapper.map(id,nickName,bitmapImage)
        }
    }

    class Test(
        private val id: String,
        private val nickName: String,
        private val image: String
    ) : CloudUser {

        fun map(mapper: UserMapper.Test) : AbstractUser {
            return mapper.map(id,nickName)
        }

        override fun <T> map(mapper: UserMapper<T>, bitmap: Bitmap)
            = throw IllegalStateException("Test.map()")
    }

    object Empty : CloudUser {
        override fun <T> map(mapper: UserMapper<T>, bitmap: Bitmap)
            = throw IllegalStateException("CloudUser.Empty")
    }
}