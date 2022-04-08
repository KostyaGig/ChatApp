package ru.zinoview.viewmodelmemoryleak.chat.data.cloud.join

import ru.zinoview.viewmodelmemoryleak.chat.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.chat.core.Clean
import ru.zinoview.viewmodelmemoryleak.chat.core.CleanRepository

interface JoinUserRepository : Clean {

    fun join(nickname: String,block: () -> Unit)

    class Base(
        private val idSharedPreferences: IdSharedPreferences,
        private val cloudDataSource: CloudDataSource
    ) : JoinUserRepository, CleanRepository(cloudDataSource)  {


        override fun join(nickname: String, block: () -> Unit) {
            cloudDataSource.join(nickname) { userId ->
                idSharedPreferences.save(userId)
                block.invoke()
            }
        }
    }
}