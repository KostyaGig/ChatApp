package ru.zinoview.viewmodelmemoryleak.data.chat.workmanager

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import ru.zinoview.viewmodelmemoryleak.data.chat.ChatAction
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudDataSource

class ChatWorkerFactory(
    private val cloudDataSource: CloudDataSource<Unit>,
    private val sendMessageAction: ChatAction,
    private val editMessageAction: ChatAction
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when(workerClassName) {
            EditMessageWorker::class.java.name -> {
                EditMessageWorker(appContext,workerParameters,cloudDataSource,editMessageAction)
            }

            else -> SendMessageWorker(appContext,workerParameters,cloudDataSource,sendMessageAction)
        }
    }
}