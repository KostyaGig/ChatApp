package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

import android.util.Log

interface Data<T> {

    fun data(
        src: T
    ) : T

    fun failure(message: String) : T

    class CloudMessage : Data<List<ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudMessage>> {

        override fun data(src: List<ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudMessage>)
            =  if (src.isEmpty()) {
                failure(EMPTY)
            } else {
                Log.d("zinoviewk","src $src")
                src
            }

        override fun failure(message: String): List<ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudMessage> {
            return listOf(
                ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudMessage.Failure(message)
            )
        }
    }

        private companion object {
            private const val EMPTY = "Chat is empty"
        }

}