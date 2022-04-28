package ru.zinoview.viewmodelmemoryleak.ui.di.data.cloud

import com.google.gson.Gson
import io.socket.client.IO
import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.*
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module

class CoreNetworkModule : Module {

    private val networkModule = module {
        single<io.socket.client.Socket> {
            IO.socket(URI)
        }

        factory<SocketConnection> {
            SocketConnection.Base(
                ActivityConnection.Base(
                    Timer.Base(),
                    ServerActivity.Base()
                )
            )
        }

        single<Json> {
            Json.Base(get())
        }

        single {
            Gson()
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(networkModule)
    }

    private companion object {
        private const val URI = "http://10.0.2.2:3000"
    }
}