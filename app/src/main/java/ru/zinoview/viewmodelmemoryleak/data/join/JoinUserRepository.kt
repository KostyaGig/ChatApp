package ru.zinoview.viewmodelmemoryleak.data.join

import ru.zinoview.viewmodelmemoryleak.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.core.Clean
import ru.zinoview.viewmodelmemoryleak.data.core.CleanRepository
import ru.zinoview.viewmodelmemoryleak.data.core.ExceptionMapper
import ru.zinoview.viewmodelmemoryleak.data.join.cloud.CloudDataSource
import java.lang.Exception

interface JoinUserRepository : Clean {

   suspend fun joinedUserId(nickname: String) : DataJoin

    class Base(
        private val idSharedPreferences: IdSharedPreferences<Int,Unit>,
        private val cloudDataSource: CloudDataSource,
        private val exceptionMapper: ExceptionMapper
    ) : JoinUserRepository, CleanRepository(cloudDataSource)  {


        override suspend fun joinedUserId(nickname: String) : DataJoin {
            return try {
                val userId = cloudDataSource.joinedUserId(nickname)
                idSharedPreferences.save(userId)
                DataJoin.Success
            } catch (e: Exception) {
                val message = exceptionMapper.map(e)
                DataJoin.Failure(message)
            }
        }
    }

    class Test(
        private val cloudDataSource: CloudDataSource
    ) : JoinUserRepository {

        override suspend fun joinedUserId(nickname: String): DataJoin {
            val userId = cloudDataSource.joinedUserId(nickname)

            return if (userId == -1) {
                DataJoin.Failure("User nickname must not be empty")
            } else {
                DataJoin.Test(userId)
            }
        }

        override fun clean()  = Unit

    }
}