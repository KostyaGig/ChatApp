package ru.zinoview.viewmodelmemoryleak.data.users

import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.core.Data
import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider
import ru.zinoview.viewmodelmemoryleak.core.users.AbstractUser
import ru.zinoview.viewmodelmemoryleak.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.data.cache.UserSharedPreferences
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
        private val prefs: UserSharedPreferences
    ) : UsersRepository {

        override suspend fun data(block: (DataUsers) -> Unit) {
            try {
                val userId = prefs.id()
                val userNickName = prefs.nickName()
                cloudDataSource.users(userId) { users ->
                    if (users.isEmpty())
                        block.invoke(DataUsers.Failure(resourceProvider.string(R.string.users_are_empty_text)))
                    else
                        block.invoke(DataUsers.Success(users.map { it.map(mapper, bitmap,userNickName) }))
                }
            } catch (e: Exception) {
                block.invoke(DataUsers.Failure(exceptionMapper.map(e)))
            }
        }
    }

}