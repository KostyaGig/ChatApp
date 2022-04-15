package ru.zinoview.viewmodelmemoryleak.data.chat

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.core.Clean
import ru.zinoview.viewmodelmemoryleak.data.core.Observe
import ru.zinoview.viewmodelmemoryleak.core.chat.EditMessage
import ru.zinoview.viewmodelmemoryleak.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.core.CleanRepository
import java.lang.Exception

interface ChatRepository : Observe<List<DataMessage>>,
    Clean,
    EditMessage {

    suspend fun sendMessage(content: String)

    suspend fun messages(block: (List<DataMessage>) -> Unit)

    class Base(
        private val cloudDataSource: CloudDataSource,
        private val mapper: CloudToDataMessageMapper,
        private val prefs: IdSharedPreferences
    ) : ChatRepository,
        CleanRepository(cloudDataSource) {

        override suspend fun sendMessage(content: String) {
            // todo process try catch
            try {
                val userId = prefs.read()
                cloudDataSource.sendMessage(userId,content)
            } catch (e: Exception) {
                Log.d("zinoviewk","ChatRepository send message exc ${e.message}")
            }
        }

        override suspend fun editMessage(messageId: String, content: String)
            = cloudDataSource.editMessage(messageId, content)

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