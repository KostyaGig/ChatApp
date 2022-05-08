package ru.zinoview.viewmodelmemoryleak.data.core

import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.core.Mapper
import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.SocketConnectionException
import ru.zinoview.viewmodelmemoryleak.data.join.EmptyNickNameException
import java.lang.Exception

interface ExceptionMapper : Mapper<Exception,String> {

    class Base(
        private val resourceProvider: ResourceProvider
    ) : ExceptionMapper {

        override fun map(src: Exception): String {
            val idString =  when(src) {
                is SocketConnectionException -> R.string.socket_connection_error
                else -> R.string.something_went_wrong
            }
            return resourceProvider.string(idString)
        }
    }

    class Join(
        private val resourceProvider: ResourceProvider
    ) : ExceptionMapper {

        override fun map(src: Exception): String {
            val idString =  when(src) {
                is SocketConnectionException -> R.string.socket_connection_error
                is EmptyNickNameException -> R.string.nickname_is_empty_text
                else -> R.string.something_went_wrong
            }
            return resourceProvider.string(idString)
        }
    }
}