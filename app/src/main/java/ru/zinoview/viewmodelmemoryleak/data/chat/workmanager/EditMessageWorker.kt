package ru.zinoview.viewmodelmemoryleak.data.chat.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ru.zinoview.viewmodelmemoryleak.data.chat.ChatAction
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudDataSource

class EditMessageWorker(
    context: Context,
    params: WorkerParameters,
    private val cloudDataSource: CloudDataSource<Unit>,
    private val action: ChatAction
) : CoroutineWorker(context,params) {

    override suspend fun doWork(): Result {
        action.editMessage(inputData,cloudDataSource)
        return Result.success()
    }

}