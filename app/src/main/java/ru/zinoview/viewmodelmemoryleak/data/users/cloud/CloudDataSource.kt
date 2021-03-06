package ru.zinoview.viewmodelmemoryleak.data.users.cloud

import ru.zinoview.viewmodelmemoryleak.core.cloud.SocketData
import ru.zinoview.viewmodelmemoryleak.core.cloud.SocketWrapper
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Json

interface CloudDataSource {

    suspend fun users(userId: String,block:(List<CloudUser>) -> Unit)

    class Base(
        private val socketWrapper: SocketWrapper,
        private val json: Json,
        private val updateUsers: UpdateUsers
    ) : CloudDataSource {

        override suspend fun users(userId: String,block:(List<CloudUser>) -> Unit) {
            socketWrapper.subscribe(USERS) { cloudData ->
                val stringJson = json.json(cloudData.first())
                val users = json.objectFromJson(stringJson,UsersDataWrapper::class.java).map()

                val excludeCurrentUserUsers = users.filterNot { user -> user.same(userId) }

                updateUsers.update(excludeCurrentUserUsers,block)
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