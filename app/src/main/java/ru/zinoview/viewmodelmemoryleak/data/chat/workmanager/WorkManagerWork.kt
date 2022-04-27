package ru.zinoview.viewmodelmemoryleak.data.chat.workmanager

import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

interface WorkManagerWork {

    fun execute(worker: OneTimeWorkRequest)

    abstract class Abstract(
        private val workManager: WorkManager,
        private val idWork: String
    ) : WorkManagerWork {

        override fun execute(worker: OneTimeWorkRequest) {
            val continuation = workManager.beginUniqueWork(idWork, ExistingWorkPolicy.APPEND,worker)
            continuation.enqueue()
        }
    }

    class Send(
        workManager: WorkManager
    ) : Abstract(workManager,ID_WORK) {

        private companion object {
            private const val ID_WORK = "SMID"
        }
    }

    class Edit(
        workManager: WorkManager
    ) : Abstract(workManager,ID_WORK) {

        private companion object {
            private const val ID_WORK = "EMID"
        }
    }

}