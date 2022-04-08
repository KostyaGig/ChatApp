package ru.zinoview.viewmodelmemoryleak.chat.data.cloud.chat

import ru.zinoview.viewmodelmemoryleak.chat.core.Clean
import ru.zinoview.viewmodelmemoryleak.chat.core.CleanRepository
import ru.zinoview.viewmodelmemoryleak.chat.core.Observe
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.IdSharedPreferences

interface ChatRepository : Observe<List<DataMessage>>, Clean {

    fun sendMessage(content: String)

    class Base(
        private val cloudDataSource: CloudDataSource,
        private val mapper: CloudToDataMessageMapper,
        private val prefs: IdSharedPreferences
    ) : ChatRepository,CleanRepository(cloudDataSource) {

        override fun sendMessage(content: String) {
            val userId = prefs.read()
            cloudDataSource.sendMessage(userId,content)
        }

        override fun observe(block: (List<DataMessage>) -> Unit) {
            cloudDataSource.observe {  cloudMessages ->
                    val dataMessages = cloudMessages.map { it.map(mapper) }
                 block.invoke(dataMessages)
            }
        }


    }
}