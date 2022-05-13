package ru.zinoview.viewmodelmemoryleak.data.users

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.core.Data
import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider
import ru.zinoview.viewmodelmemoryleak.core.users.AbstractUser
import ru.zinoview.viewmodelmemoryleak.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.data.core.ExceptionMapper
import ru.zinoview.viewmodelmemoryleak.data.users.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.users.cloud.CloudMessageMapper
import java.lang.Exception

interface UsersRepository : Data<DataUsers> {

    class Base(
        private val cloudDataSource: CloudDataSource,
        private val mapper: CloudMessageMapper<AbstractUser>,
        private val bitmap: ru.zinoview.viewmodelmemoryleak.ui.join.Bitmap,
        private val exceptionMapper: ExceptionMapper,
        private val resourceProvider: ResourceProvider,
        private val prefs: IdSharedPreferences<String,Unit>
    ) : UsersRepository {

        override suspend fun data(): DataUsers {
            return try {
                val userId = prefs.read(Unit)
                val users = cloudDataSource.users(userId).map { cloudUser -> cloudUser.map(mapper, bitmap) }
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