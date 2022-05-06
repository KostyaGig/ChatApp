package ru.zinoview.viewmodelmemoryleak.data.join.cloud

import ru.zinoview.viewmodelmemoryleak.core.cloud.SocketData
import ru.zinoview.viewmodelmemoryleak.core.cloud.SocketWrapper
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.AbstractCloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Disconnect
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Json
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface CloudDataSource : Disconnect<Unit>, AbstractCloudDataSource {

    suspend fun joinedUserId(nickname: String) : String

    class Base(
        private val socketWrapper: SocketWrapper,
        private val json: Json
    ) : AbstractCloudDataSource.Base(socketWrapper), CloudDataSource {

        override suspend fun joinedUserId(nickname: String) : String {
            return suspendCoroutine { continuation ->
                socketWrapper.subscribe(JOIN_USER) { data ->
                    val id = data.first() as Int
                    continuation.resume(id.toString())
                }

                val user = SocketData.Base(json.json(
                    Pair(
                        NICKNAME_KEY,
                        nickname
                    )
                ))

                socketWrapper.emit(JOIN_USER,user)
            }
        }

        private companion object {
            private const val JOIN_USER = "join_user"

            private const val NICKNAME_KEY = "nickname"
        }
    }
}