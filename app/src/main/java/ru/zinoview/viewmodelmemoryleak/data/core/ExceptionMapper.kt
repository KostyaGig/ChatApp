package ru.zinoview.viewmodelmemoryleak.data.core

import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.SocketConnectionException
import java.lang.Exception

interface ExceptionMapper {

    fun map(exception: Exception) : String

    class Base(
        private val resourceProvider: ResourceProvider
    ) : ExceptionMapper {

        override fun map(exception: Exception): String {
            val idString =  when(exception) {
                is SocketConnectionException -> R.string.sockket_connection_error
                else -> R.string.something_went_wrong
            }
            return resourceProvider.string(idString)
        }
    }
}