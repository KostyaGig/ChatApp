package ru.zinoview.viewmodelmemoryleak.core

import android.content.Context
import androidx.work.*
import com.google.gson.Gson
import io.socket.client.IO
import ru.zinoview.viewmodelmemoryleak.data.cache.Id
import ru.zinoview.viewmodelmemoryleak.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.data.cache.SharedPreferencesReader
import ru.zinoview.viewmodelmemoryleak.data.chat.ChatAction
import ru.zinoview.viewmodelmemoryleak.data.chat.ChatWorkerFactory
import ru.zinoview.viewmodelmemoryleak.data.chat.Worker
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.*
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.Data
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.*

interface WorkManager {

    fun workManager(context: Context) : Configuration

    class Base : WorkManager {
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
                    ToCloudProgressMessageMapper(),
                    IsNotEmpty.List(),
                    ListSize.Base(),
                    IdSharedPreferences.Base(
                        SharedPreferencesReader.Id(
                            Id.Base()
                        ),
                        Id.Base(),
                        context
                    )
                )
            )
            val workManager = androidx.work.WorkManager.getInstance(context)
            val worker = Worker.Chat(workManager)
            val factory = ChatWorkerFactory(
                cloudDataSource, ChatAction.SendMessage(worker),
                ChatAction.EditMessage(worker)
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