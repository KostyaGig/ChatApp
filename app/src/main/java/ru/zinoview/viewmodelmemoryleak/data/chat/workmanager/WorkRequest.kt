package ru.zinoview.viewmodelmemoryleak.data.chat.workmanager

import androidx.work.*

interface WorkRequest {

    fun oneTimeWorkRequest(data: Data) : OneTimeWorkRequest

    interface Network : WorkRequest {

        abstract class Abstract<T : ListenableWorker>(
            private val clazz: Class<T>
        ): Network {


            override fun  oneTimeWorkRequest(data: Data): OneTimeWorkRequest {
                val constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()

                return OneTimeWorkRequest.Builder(clazz)
                    .setInputData(data)
                    .setConstraints(constraints)
                    .build()
            }
        }

        class Edit : Network.Abstract<EditMessageWorker>(EditMessageWorker::class.java)
        class Send : Network.Abstract<SendMessageWorker>(SendMessageWorker::class.java)

    }
}