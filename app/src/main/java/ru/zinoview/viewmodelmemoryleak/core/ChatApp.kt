package ru.zinoview.viewmodelmemoryleak.core

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.Module
import ru.zinoview.viewmodelmemoryleak.ui.chat.notification.*
import ru.zinoview.viewmodelmemoryleak.ui.di.core.CoreModule
import ru.zinoview.viewmodelmemoryleak.ui.di.data.cache.CoreCacheModule
import ru.zinoview.viewmodelmemoryleak.ui.di.data.chat.DataModule
import ru.zinoview.viewmodelmemoryleak.ui.di.data.chat.NetworkModule
import ru.zinoview.viewmodelmemoryleak.ui.di.data.cloud.CoreNetworkModule
import ru.zinoview.viewmodelmemoryleak.ui.di.data.core.CoreDataModule
import ru.zinoview.viewmodelmemoryleak.ui.di.domain.chat.DomainModule
import ru.zinoview.viewmodelmemoryleak.ui.di.ui.chat.UiModule
import ru.zinoview.viewmodelmemoryleak.ui.di.ui.core.CoreUiModule

class ChatApp : Application(), Configuration.Provider {

    private var notification: ShowNotification = ShowNotification.Empty

    override fun onCreate() {
        super.onCreate()

        val resourceProvider = ResourceProvider.Base(this)
        val channelId = GroupId.Base(this)
        val channel = Channel.Base(channelId,UniqueNotification.Base(
            this,resourceProvider, NotificationId.Base(), Date.Notification()))
        val service = NotificationService.Base(this)
        notification = ShowNotification.Base(
            Notification.Base(
                channel,
                NotificationMapper.Base(GroupId.Notification(channel))
            ),
            service
        )

        val coreNetworkModule = CoreNetworkModule()
        val coreCacheModule = CoreCacheModule(this)
        val coreModule = CoreModule(this)
        val connectionNetworkModule = ru.zinoview.viewmodelmemoryleak.ui.di.data.connection.NetworkModule()

        val chatDataModule = DataModule(this)
        val uiStateDataModule = ru.zinoview.viewmodelmemoryleak.ui.di.data.chat.ui_state.DataModule(this)
        val chatNetworkModule = NetworkModule(this)

        val coreDataModule = CoreDataModule(notification)
        val joinDataModule = ru.zinoview.viewmodelmemoryleak.ui.di.data.join.DataModule()
        val joinNetworkModule = ru.zinoview.viewmodelmemoryleak.ui.di.data.join.NetworkModule()

        val userStatusDataModule = ru.zinoview.viewmodelmemoryleak.ui.di.data.chat.user_status.DataModule()
        val userStatusNetworkModule = ru.zinoview.viewmodelmemoryleak.ui.di.data.chat.user_status.NetworkModule()

        val authenticationDataModule = ru.zinoview.viewmodelmemoryleak.ui.di.data.authentication.DataModule()

        val updateDataModule = ru.zinoview.viewmodelmemoryleak.ui.di.data.chat.update.DataModule()
        val updateNetworkModule = ru.zinoview.viewmodelmemoryleak.ui.di.data.chat.update.NetworkModule()

        val processingMessagesDataModule = ru.zinoview.viewmodelmemoryleak.ui.di.data.chat.processing_messages.DataModule()
        val processingMessagesNetworkModule = ru.zinoview.viewmodelmemoryleak.ui.di.data.chat.processing_messages.NetworkModule()

        val chatDomainModule = DomainModule()

        val coreUiModule = CoreUiModule()
        val chatUiModule = UiModule()
        val authenticationUiModule = ru.zinoview.viewmodelmemoryleak.ui.di.ui.authentication.UiModule(this)
        val joinUiModule = ru.zinoview.viewmodelmemoryleak.ui.di.ui.join.UiModule()
        val connectionUiModule = ru.zinoview.viewmodelmemoryleak.ui.di.ui.connection.UiModule()

        val modules = mutableListOf<ru.zinoview.viewmodelmemoryleak.ui.di.core.Module>()

        modules.add(coreNetworkModule)
        modules.add(coreCacheModule)
        modules.add(coreModule)
        modules.add(coreDataModule)
        modules.add(chatDataModule)
        modules.add(uiStateDataModule)
        modules.add(chatNetworkModule)
        modules.add(joinDataModule)
        modules.add(joinNetworkModule)
        modules.add(userStatusDataModule)
        modules.add(userStatusNetworkModule)
        modules.add(authenticationDataModule)
        modules.add(connectionNetworkModule)
        modules.add(updateDataModule)
        modules.add(updateNetworkModule)
        modules.add(processingMessagesDataModule)
        modules.add(processingMessagesNetworkModule)
        modules.add(chatDomainModule)
        modules.add(coreUiModule)
        modules.add(chatUiModule)
        modules.add(authenticationUiModule)
        modules.add(joinUiModule)
        modules.add(connectionUiModule)

        val koinModules = mutableListOf<Module>()
        modules.forEach { module ->
            module.add(koinModules)
        }


        startKoin(this,koinModules)
        WorkManager.initialize(this,workManagerConfiguration)
    }


    override fun getWorkManagerConfiguration()
        = ru.zinoview.viewmodelmemoryleak.core.WorkManager.Base(notification).workManager(this)
}