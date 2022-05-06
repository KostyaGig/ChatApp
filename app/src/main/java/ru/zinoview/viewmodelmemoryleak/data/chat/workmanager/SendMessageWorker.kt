package ru.zinoview.viewmodelmemoryleak.data.chat.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ru.zinoview.viewmodelmemoryleak.data.chat.ChatAction
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudDataSource

class SendMessageWorker(
    context: Context,
    params: WorkerParameters,
    private val cloudDataSource: CloudDataSource<Unit>,
    private val action: ChatAction,
) : CoroutineWorker(context,params) {


    override suspend fun doWork(): Result {
        action.sendMessage(inputData,cloudDataSource)
        return Result.success()
    }

}