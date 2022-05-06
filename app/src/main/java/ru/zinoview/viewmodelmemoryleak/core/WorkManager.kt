package ru.zinoview.viewmodelmemoryleak.core

import android.content.Context
import androidx.work.*
import com.google.gson.Gson
import io.socket.client.IO
import ru.zinoview.viewmodelmemoryleak.core.cloud.SocketWrapper
import ru.zinoview.viewmodelmemoryleak.data.cache.Id
import ru.zinoview.viewmodelmemoryleak.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.data.chat.ChatAction
import ru.zinoview.viewmodelmemoryleak.data.chat.workmanager.ChatWorkerFactory
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.*
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.Data
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.ProcessingMessages
import ru.zinoview.viewmodelmemoryleak.data.core.EmptyString
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.*
import ru.zinoview.viewmodelmemoryleak.ui.chat.notification.NotificationService
import ru.zinoview.viewmodelmemoryleak.ui.chat.notification.ShowNotification

interface WorkManager {

    fun workManager(context: Context) : Configuration

    class Base(
        private val notification: ShowNotification
    ) : WorkManager {
        override fun workManager(context: Context): Configuration {

            val emptyString = EmptyString.Base()
            val cloudDataSource = CloudDataSource.Base(
                SocketWrapper.Base(
                    IO.socket(URI),
                    SocketConnection.Base(
                        ActivityConnection.Base(
                            Timer.Base()
                        )
                    )
                ),
                Json.Base(
                    Gson()
                ),
                Data.CloudMessage(),
                MessagesStore.Base(
                    ListItem.Base(),
                    ToProgressEditMessageMapper(
                        Time.String()
                    ),
                    IsNotEmpty.List(),
                    ListSize.Base(),
                    IdSharedPreferences.Base(
                        Id.Base(emptyString),
                        context
                    )
                ),
                ProcessingMessages.Empty,
                NotificationService.Push(context)
            )
            val factory = ChatWorkerFactory(
                cloudDataSource,
                ChatAction.SendMessage(notification),
                ChatAction.EditMessage(
                    notification)
            )

            return Configuration.Builder()
                .setWorkerFactory(factory)
                .build()
        }
    }

    private companion object {
        private const val URI = "http://10.0.2.2:3000"
    }
}