package ru.zinoview.viewmodelmemoryleak.core.chat.notification

interface MessageNotification {

    fun <T> map(mapper: Mapper<T>) : T

    interface Mapper<T> {
        fun map(time: String,content: String) : T
    }
}