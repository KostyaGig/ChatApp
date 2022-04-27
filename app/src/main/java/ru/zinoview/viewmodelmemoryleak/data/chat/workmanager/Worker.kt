package ru.zinoview.viewmodelmemoryleak.data.chat.workmanager

import androidx.work.*

interface Worker {

    fun sendMessage(pairs: List<Pair<String,String>>)
    fun editMessage(pairs: List<Pair<String,String>>)

    class Chat(
        private val sendWorkRequest: WorkRequest,
        private val editWorkRequest: WorkRequest,
        private val sendWork: WorkManagerWork,
        private val editWork: WorkManagerWork,
    ) : Worker {

        override fun sendMessage(pairs: List<Pair<String,String>>) {
            val inputDataBuilder = Data.Builder()
            pairs.forEach { pair ->
                inputDataBuilder.putString(pair.first,pair.second)
            }

            val worker = sendWorkRequest.oneTimeWorkRequest(inputDataBuilder.build())

            sendWork.execute(worker)
        }

        override fun editMessage(pairs: List<Pair<String,String>>) {
            val inputDataBuilder = Data.Builder()
            pairs.forEach { pair ->
                inputDataBuilder.putString(pair.first,pair.second)
            }

            val worker = editWorkRequest.oneTimeWorkRequest(inputDataBuilder.build())
            editWork.execute(worker)
        }
    }
}