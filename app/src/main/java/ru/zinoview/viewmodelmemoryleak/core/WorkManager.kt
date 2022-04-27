package ru.zinoview.viewmodelmemoryleak.core

import android.content.Context
import androidx.work.*
import com.google.gson.Gson
import io.socket.client.IO
import ru.zinoview.viewmodelmemoryleak.data.cache.Id
import ru.zinoview.viewmodelmemoryleak.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.data.cache.SharedPreferencesReader
import ru.zinoview.viewmodelmemoryleak.data.chat.ChatAction
import ru.zinoview.viewmodelmemoryleak.data.chat.workmanager.ChatWorkerFactory
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.*
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.Data
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.ProcessingMessages
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.*
import ru.zinoview.viewmodelmemoryleak.ui.chat.ToUiMessageMapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.notification.ShowNotification

interface WorkManager {

    fun workManager(context: Context) : Configuration

    class Base(
        private val notification: ShowNotification
    ) : WorkManager {
        override fun workManager(context: Context): Configuration {

            val cloudDataSource = CloudDataSource.Base(
                IO.socket(URI),
                SocketConnection.Base(
                    ActivityConnection.Base(
                        Timer.Base(),
                        ServerActivity.Base()
                    )
                ),
                Json.Base(),
                Gson(),
                Data.CloudMessage(),
                MessagesStore.Base(
                    ListItem.Base(),
                    ToProgressEditMessageMapper(),
                    IsNotEmpty.List(),
                    ListSize.Base(),
                    IdSharedPreferences.Base(
                        SharedPreferencesReader.Id(
                            Id.Base()
                        ),
                        Id.Base(),
                        context
                    ),
                    ToUiMessageMapper()
                ),
                ProcessingMessages.Empty
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