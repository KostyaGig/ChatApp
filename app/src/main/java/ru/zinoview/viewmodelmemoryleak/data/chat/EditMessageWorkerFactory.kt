package ru.zinoview.viewmodelmemoryleak.data.chat

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.EditMessageWorker

class EditMessageWorkerFactory(
    private val cloudDataSource: CloudDataSource<Unit>,
    private val action: ChatAction
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {

        return when(workerClassName) {
            EditMessageWorker::class.java.name -> {
                EditMessageWorker(appContext,workerParameters,cloudDataSource,action)
            }

            else -> SendMessageWorker(appContext,workerParameters,cloudDataSource,action)
        }
    }
}