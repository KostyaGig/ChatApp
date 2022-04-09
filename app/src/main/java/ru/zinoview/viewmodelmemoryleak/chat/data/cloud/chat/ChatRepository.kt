package ru.zinoview.viewmodelmemoryleak.chat.data.cloud.chat

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.chat.core.Clean
import ru.zinoview.viewmodelmemoryleak.chat.core.CleanRepository
import ru.zinoview.viewmodelmemoryleak.chat.core.Observe
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.IdSharedPreferences
import java.lang.Exception

interface ChatRepository : Observe<List<DataMessage>>, Clean {

    fun sendMessage(content: String)

    suspend fun messages(block: (List<DataMessage>) -> Unit)

    class Base(
        private val cloudDataSource: CloudDataSource,
        private val mapper: CloudToDataMessageMapper,
        private val prefs: IdSharedPreferences
    ) : ChatRepository,CleanRepository(cloudDataSource) {

        override fun sendMessage(content: String) {
            try {
                val userId = prefs.read()
                cloudDataSource.sendMessage(userId,content)
            } catch (e: Exception) {
                Log.d("zinoviewk","ChatRepository send message exc ${e.message}")
            }
        }

        override suspend fun messages(block: (List<DataMessage>) -> Unit) {
            cloudDataSource.messages { cloud ->
                val data = cloud.map { it.map(mapper) }
                block.invoke(data)
            }
        }

        override fun observe(block: (List<DataMessage>) -> Unit) {
            try {
                cloudDataSource.observe {  cloudMessages ->
                    val dataMessages = cloudMessages.map { it.map(mapper) }
                    block.invoke(dataMessages)
                }
            } catch (e: Exception) {
                Log.d("zinoviewk","ChatRepository observe exc ${e.message}")
            }

        }


    }
}