package ru.zinoview.viewmodelmemoryleak.chat.data.cloud.join

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.chat.core.Clean
import ru.zinoview.viewmodelmemoryleak.chat.core.CleanRepository
import java.lang.Exception

interface JoinUserRepository : Clean {

    fun join(nickname: String,block: (DataJoin) -> Unit)

    class Base(
        private val idSharedPreferences: IdSharedPreferences,
        private val cloudDataSource: CloudDataSource
    ) : JoinUserRepository, CleanRepository(cloudDataSource)  {


        override fun join(nickname: String, block: (DataJoin) -> Unit) {
            try {
                cloudDataSource.join(nickname) { userId ->
                    idSharedPreferences.save(userId)
                    block.invoke(DataJoin.Success)
                }
            } catch (e: Exception) {
                block.invoke(
                    DataJoin.Failure
                )
            }
        }
    }
}