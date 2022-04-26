package ru.zinoview.viewmodelmemoryleak.data.chat

import androidx.work.*
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.EditMessageWorker

interface Worker {

    fun sendMessage(pairs: List<Pair<String,String>>)
    fun editMessage(pairs: List<Pair<String,String>>)

    class Chat(
        private val workManager: WorkManager
    ) : Worker {

        override fun sendMessage(pairs: List<Pair<String,String>>) {
            val inputDataBuilder = Data.Builder()
            pairs.forEach { pair ->
                inputDataBuilder.putString(pair.first,pair.second)
            }

            val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

            val worker = OneTimeWorkRequestBuilder<SendMessageWorker>()
                .setInputData(inputDataBuilder.build())
                .setConstraints(constraints)
                .build()

            // todo constant
            val continuation = workManager.beginUniqueWork("sequence",ExistingWorkPolicy.APPEND,worker)
            continuation.enqueue()
        }

        override fun editMessage(pairs: List<Pair<String,String>>) {
            val inputDataBuilder = Data.Builder()
            pairs.forEach { pair ->
                inputDataBuilder.putString(pair.first,pair.second)
            }

            val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

            val worker = OneTimeWorkRequestBuilder<EditMessageWorker>()
                .setInputData(inputDataBuilder.build())
                .setConstraints(constraints)
                .build()

            workManager.enqueue(worker)
        }
    }
}