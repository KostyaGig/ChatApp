package ru.zinoview.viewmodelmemoryleak.data.chat.workmanager

import androidx.work.*

interface WorkRequest {

    fun oneTimeWorkRequest(data: Data) : OneTimeWorkRequest

    interface Network : WorkRequest {

//        class Abstract : Network {
//
//            override fun oneTimeWorkRequest(data: Data): OneTimeWorkRequest {
//                val constraints = Constraints.Builder()
//                    .setRequiredNetworkType(NetworkType.CONNECTED)
//                    .build()
//
//                return OneTimeWorkRequestBuilder<>()
//                    .setInputData(data)
//                    .setConstraints(constraints)
//                    .build()
//            }
//        }

        class Edit : Network {
            override fun oneTimeWorkRequest(data: Data): OneTimeWorkRequest {
                val constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()

                return OneTimeWorkRequestBuilder<EditMessageWorker>()
                    .setInputData(data)
                    .setConstraints(constraints)
                    .build()
            }
        }

        class Send : Network {

            override fun oneTimeWorkRequest(data: Data): OneTimeWorkRequest {
                val constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()

                return OneTimeWorkRequestBuilder<SendMessageWorker>()
                    .setInputData(data)
                    .setConstraints(constraints)
                    .build()
            }
        }

    }
}