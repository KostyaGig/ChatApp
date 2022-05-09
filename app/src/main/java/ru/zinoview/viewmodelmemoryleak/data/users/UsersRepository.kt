package ru.zinoview.viewmodelmemoryleak.data.users

import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.core.Data
import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider
import ru.zinoview.viewmodelmemoryleak.data.core.ExceptionMapper
import ru.zinoview.viewmodelmemoryleak.data.users.cloud.CloudDataSource
import java.lang.Exception

interface UsersRepository : Data<DataUsers> {

    // todo test
    class Base(
        private val cloudDataSource: CloudDataSource,
        private val mapper: CloudToAbstractUserMapper,
        private val bitmap: ru.zinoview.viewmodelmemoryleak.ui.join.Bitmap,
        private val exceptionMapper: ExceptionMapper,
        private val resourceProvider: ResourceProvider
    ) : UsersRepository {

        override suspend fun data(): DataUsers {
            return try {
                val users = cloudDataSource.data().map { cloudUser -> cloudUser.map(mapper, bitmap) }
                return if (users.isEmpty()) {
                    DataUsers.Failure(resourceProvider.string(R.string.users_are_empty_text))
                } else {
                    DataUsers.Success(users)
                }
            } catch (e: Exception) {
                DataUsers.Failure(exceptionMapper.map(e))
            }
        }
    }

}