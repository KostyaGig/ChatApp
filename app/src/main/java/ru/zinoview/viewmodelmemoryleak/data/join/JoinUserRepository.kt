package ru.zinoview.viewmodelmemoryleak.data.join

import ru.zinoview.viewmodelmemoryleak.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.core.Clean
import ru.zinoview.viewmodelmemoryleak.core.join.JoinUserId
import ru.zinoview.viewmodelmemoryleak.data.cache.NickNameSharedPreferences
import ru.zinoview.viewmodelmemoryleak.data.core.CleanRepository
import ru.zinoview.viewmodelmemoryleak.data.core.ExceptionMapper
import ru.zinoview.viewmodelmemoryleak.data.join.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.ui.join.ImageProfile
import java.lang.Exception

interface JoinUserRepository : Clean, JoinUserId<DataJoin> {

    class Base(
        private val idSharedPreferences: IdSharedPreferences<String, Unit>,
        private val nickNameSharedPreferences: NickNameSharedPreferences<String, Unit>,
        private val cloudDataSource: CloudDataSource,
        private val exceptionMapper: ExceptionMapper,
        private val mapper: CloudIdToDataJoinMapper
    ) : JoinUserRepository, CleanRepository(cloudDataSource) {

        override fun joinedUserId(
            image: ImageProfile,
            nickname: String,
            block: (DataJoin) -> Unit
        ) = try {
            cloudDataSource.joinedUserId(image, nickname) { cloudId ->
                cloudId.save(idSharedPreferences)
                nickNameSharedPreferences.save(nickname)
                val dataJoin = cloudId.map(mapper)
                block.invoke(dataJoin)
            }
        } catch (e: Exception) {
            val message = exceptionMapper.map(e)
            block.invoke(DataJoin.Failure(message))
        }
    }

}