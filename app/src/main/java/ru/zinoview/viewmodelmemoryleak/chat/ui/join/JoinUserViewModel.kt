package ru.zinoview.viewmodelmemoryleak.chat.ui.join
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Dispatcher

interface JoinUserViewModel {

    fun disconnect()

    fun joinUser(nickname: String,block:() -> Unit)

    class Base(
        private val cloudDataSource: CloudDataSource.JoinUser,
        private val dispatcher: Dispatcher
    ) : ViewModel(), JoinUserViewModel {

        // todo use livedata
        private var block: (() -> Unit)? = null

        override fun joinUser(nickname: String,block:() -> Unit) {
            this.block = block
            dispatcher.doBackground(viewModelScope) {
                cloudDataSource.joinUser(nickname,block)
            }
        }

        override fun disconnect() {
            block = null
            cloudDataSource.disconnect()
        }


    }
}