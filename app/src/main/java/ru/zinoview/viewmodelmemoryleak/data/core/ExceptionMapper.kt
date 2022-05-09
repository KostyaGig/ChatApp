package ru.zinoview.viewmodelmemoryleak.data.core

import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.core.Mapper
import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.SocketConnectionException
import ru.zinoview.viewmodelmemoryleak.data.join.EmptyNickNameException
import ru.zinoview.viewmodelmemoryleak.data.users.cloud.EmptyUsersException
import java.lang.Exception

interface ExceptionMapper : Mapper<Exception,String> {

    abstract class Abstract(
        private val resourceProvider: ResourceProvider
    ) : ExceptionMapper {

        abstract fun errorMessageId(e: Exception) : Int

        override fun map(src: Exception): String {
            val idString =  when(src) {
                is SocketConnectionException -> R.string.socket_connection_error
                else -> errorMessageId(src)
            }
            return resourceProvider.string(idString)
        }

        class Base(
            resourceProvider: ResourceProvider
        ) : Abstract(resourceProvider) {
            override fun errorMessageId(e: Exception): Int = -1
        }

        class Join(
            resourceProvider: ResourceProvider
        ) : Abstract(resourceProvider) {

            override fun errorMessageId(e: Exception): Int {
                return when(e) {
                    is EmptyNickNameException -> R.string.nickname_is_empty_text
                    else -> R.string.something_went_wrong
                }
            }
        }

        class User(
            resourceProvider: ResourceProvider
        ) : Abstract(resourceProvider) {

            override fun errorMessageId(e: Exception): Int {
                return when(e) {
                    is EmptyUsersException -> R.string.users_are_empty_text
                    else -> R.string.something_went_wrong
                }
            }
        }
    }
}