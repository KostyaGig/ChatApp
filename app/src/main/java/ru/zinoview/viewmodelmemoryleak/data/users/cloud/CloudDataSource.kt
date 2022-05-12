package ru.zinoview.viewmodelmemoryleak.data.users.cloud

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.core.cloud.SocketData
import ru.zinoview.viewmodelmemoryleak.core.cloud.SocketWrapper
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Json
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface CloudDataSource {

    suspend fun users(userId: String) : List<CloudUser>

    class Base(
        private val socketWrapper: SocketWrapper,
        private val json: Json,
    ) : CloudDataSource {

        override suspend fun users(userId: String) = suspendCoroutine<List<CloudUser>> {  continuation ->
            socketWrapper.subscribe(USERS) { cloudData ->
                val stringJson = json.json(cloudData.first())
                Log.d("zinoviewk","json users $stringJson")
                val users = json.objectFromJson(stringJson,UsersDataWrapper::class.java).map()
                continuation.resume(users)
                socketWrapper.disconnect(USERS)
            }

            val json = json.json(
                Pair(USER_ID_KEY,userId)
            )
            socketWrapper.emit(USERS,SocketData.Base(json))
        }

        private companion object {
            private const val USERS = "users"

            private const val USER_ID_KEY = "userId"
        }
    }
}