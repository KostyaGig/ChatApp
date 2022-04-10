package ru.zinoview.viewmodelmemoryleak.chat.data.join

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.chat.core.Clean
import ru.zinoview.viewmodelmemoryleak.chat.core.CleanRepository
import ru.zinoview.viewmodelmemoryleak.chat.core.ExceptionMapper
import ru.zinoview.viewmodelmemoryleak.chat.data.join.cloud.CloudDataSource
import java.lang.Exception

interface JoinUserRepository : Clean {

    fun join(nickname: String,block: (DataJoin) -> Unit)

    class Base(
        private val idSharedPreferences: IdSharedPreferences,
        private val cloudDataSource: CloudDataSource,
        private val exceptionMapper: ExceptionMapper
    ) : JoinUserRepository, CleanRepository(cloudDataSource)  {


        override fun join(nickname: String, block: (DataJoin) -> Unit) {
            try {
                cloudDataSource.join(nickname) { userId ->
                    idSharedPreferences.save(userId)
                    Log.d("zinoviewk","repo block.invoke, uid - $userId")
                    block.invoke(DataJoin.Success)
                }
            } catch (e: Exception) {
                val message = exceptionMapper.map(e)
                block.invoke(
                    DataJoin.Failure(message)
                )
                Log.d("zinoviewk","try catch e message = $message")
            }
        }
    }
}