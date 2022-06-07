package ru.zinoview.viewmodelmemoryleak.data.users.cloud

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.core.users.UserBitmap
import ru.zinoview.viewmodelmemoryleak.core.users.UserMapper
import ru.zinoview.viewmodelmemoryleak.ui.core.SameOne
import ru.zinoview.viewmodelmemoryleak.ui.join.Bitmap
import java.lang.IllegalStateException

interface CloudUser : SameOne<String> {

    fun <T> map(mapper: CloudMessageMapper<T>, bitmap: Bitmap,userNickName: String) : T

    fun <T> map(mapper: UserMapper<T>,bitmap: Bitmap) : T

    override fun same(data: String) = false

    fun isNotUpdated() : Boolean = false

    open class Base(
        private val id: String,
        private val actualNickName: String,
        private val senderNickName: String,
        private val receiverNickName: String,
        private val image: String,
        private val lastMessage: String? = null,
        private val lastMessageSenderNickName: String? = null,
        private val update: Boolean = true,
        ) : CloudUser {

        override fun same(data: String) = this.id == data

        override fun isNotUpdated() = update.not()
        object Empty : Base("","","","","","")


        override fun <T> map(mapper: UserMapper<T>,bitmap: Bitmap) : T {
            val bitmapImage = bitmap.bitmap(image)
            return mapper.map(
                id,
                senderNickName,
                "",
                UserBitmap.Base(bitmapImage)
            )
        }

        override fun <T> map(mapper: CloudMessageMapper<T>, bitmap: Bitmap,userNickName: String): T {
            val bitmapImage = bitmap.bitmap(image)
            val cloudLastMessage = CloudLastMessage.Base(userNickName,lastMessage,lastMessageSenderNickName)
            return if (senderNickName.isEmpty() and receiverNickName.isEmpty()) {
                mapper.map(id,actualNickName,cloudLastMessage,bitmapImage)
            } else {
                val nickName = if (userNickName == senderNickName) receiverNickName else senderNickName
               Log.d("zinoviewk","MAP ACTUAL NAME LAST MSG $lastMessage")
                mapper.map(id,actualNickName,cloudLastMessage,bitmapImage)
            }
        }

    class Test(
        private val id: String,
        private val nickName: String,
        private val image: String
    ) : CloudUser {

        fun map(mapper: UserMapper.Test) = mapper.map(id,nickName)


        override fun <T> map(mapper: UserMapper<T>, bitmap: Bitmap)
            = throw IllegalStateException("Test.map()")

        override fun <T> map(mapper: CloudMessageMapper<T>, bitmap: Bitmap,userNickName: String)
            = throw IllegalStateException("Test.map(CloudMessageMapper)")
        }
    }

    object Empty : CloudUser {
        override fun <T> map(mapper: UserMapper<T>, bitmap: Bitmap)
            = throw IllegalStateException("CloudUser.Empty")

        override fun <T> map(mapper: CloudMessageMapper<T>, bitmap: Bitmap,userNickName: String)
            = throw IllegalStateException("Empty.map(CloudMessageMapper)")
    }
}