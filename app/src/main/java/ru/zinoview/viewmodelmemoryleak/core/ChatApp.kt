package ru.zinoview.viewmodelmemoryleak.core

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.Module
import ru.zinoview.viewmodelmemoryleak.ui.chat.notification.*
import ru.zinoview.viewmodelmemoryleak.ui.core.koin_scope.KoinScope
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
        val channel = Channel.Base(this)
        channel.createChannel()

        val service = NotificationService.Base(this)


        notification = ShowNotification.Base(
            NotificationWrappers.Base(
                NotificationMapper.Base(Channel.Processing(
                    UniqueNotification.Base(
                        resourceProvider, NotificationId.Base(), Date.Notification(),Notification.Base(
                            this
                        )
                    )
                ))
            ),
            service
        )

        val coreNetworkModule = CoreNetworkModule()
        val coreCacheModule = CoreCacheModule(this)
        val coreModule = CoreModule(this)
        val connectionNetworkModule = ru.zinoview.viewmodelmemoryleak.ui.di.data.connection.NetworkModule()

        val chatDataModule = DataModule(this)
        val chatUiStateDataModule =
            ru.zinoview.viewmodelmemoryleak.ui.di.data.chat.ui_state.DataModule(this)
        val chatNetworkModule = NetworkModule(this)

        val coreDataModule = CoreDataModule(notification,this.contentResolver)
        val joinUiStateDataModule = ru.zinoview.viewmodelmemoryleak.ui.di.data.join.ui_state.DataModule(this)
        val joinDataModule = ru.zinoview.viewmodelmemoryleak.ui.di.data.join.DataModule(this)
        val joinNetworkModule = ru.zinoview.viewmodelmemoryleak.ui.di.data.join.NetworkModule()

        val userStatusDataModule = ru.zinoview.viewmodelmemoryleak.ui.di.data.chat.user_status.DataModule()
        val userStatusNetworkModule = ru.zinoview.viewmodelmemoryleak.ui.di.data.chat.user_status.NetworkModule()

        val authenticationDataModule = ru.zinoview.viewmodelmemoryleak.ui.di.data.authentication.DataModule()

        val updateDataModule = ru.zinoview.viewmodelmemoryleak.ui.di.data.chat.update.DataModule()
        val updateNetworkModule = ru.zinoview.viewmodelmemoryleak.ui.di.data.chat.update.NetworkModule()

        val processingMessagesDataModule = ru.zinoview.viewmodelmemoryleak.ui.di.data.chat.processing_messages.DataModule()
        val processingMessagesNetworkModule = ru.zinoview.viewmodelmemoryleak.ui.di.data.chat.processing_messages.NetworkModule()

        val koinScope = KoinScope.Base()

        val usersNetworkModule = ru.zinoview.viewmodelmemoryleak.ui.di.data.users.NetworkModule()
        val usersDataModule = ru.zinoview.viewmodelmemoryleak.ui.di.data.users.DataModule()
        val usersDomainModule = ru.zinoview.viewmodelmemoryleak.ui.di.domain.users.DomainModule()
        val usersUiModule = ru.zinoview.viewmodelmemoryleak.ui.di.ui.users.UiModule()

        val chatDomainModule = DomainModule()
        val connectionDomainModule = ru.zinoview.viewmodelmemoryleak.ui.di.domain.connection.DomainModule()

        val coreUiModule = CoreUiModule()

        val scope = CoroutineScope(Job())
        val chatUiModule = UiModule(scope)

        val authenticationUiModule = ru.zinoview.viewmodelmemoryleak.ui.di.ui.authentication.UiModule(this)
        val joinUiModule = ru.zinoview.viewmodelmemoryleak.ui.di.ui.join.UiModule()
        val connectionUiModule = ru.zinoview.viewmodelmemoryleak.ui.di.ui.connection.UiModule()

        val modules = mutableListOf<ru.zinoview.viewmodelmemoryleak.ui.di.core.Module>()

        modules.add(coreNetworkModule)
        modules.add(coreCacheModule)
        modules.add(coreModule)
        modules.add(coreDataModule)
        modules.add(chatDataModule)
        modules.add(chatUiStateDataModule)
        modules.add(chatNetworkModule)
        modules.add(joinDataModule)
        modules.add(joinUiStateDataModule)
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
        modules.add(connectionDomainModule)
        modules.add(coreUiModule)
        modules.add(chatUiModule)
        modules.add(authenticationUiModule)
        modules.add(joinUiModule)
        modules.add(connectionUiModule)
        modules.add(usersNetworkModule)
        modules.add(usersDataModule)
        modules.add(usersDomainModule)
        modules.add(usersUiModule)

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