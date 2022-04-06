package ru.zinoview.viewmodelmemoryleak.chat.ui
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.CloudModel

interface ChatViewModel {

    fun emit()

    fun observe(block: (CloudModel) -> Unit)

    fun observeFirst(block: (CloudModel) -> Unit)

    fun disconnect()

    fun joinUser(nickname: String)

    class Base(
        private val cloudDataSource: CloudDataSource,
        private val dispatcher: Dispatcher
    ) : ViewModel(),ChatViewModel {

        init {
            Log.d("zinoviewk","newViewMdodel")
        }

        private var block: ((CloudModel) -> Unit)? = null

        override fun emit() = dispatcher.doBackground(viewModelScope) { cloudDataSource.emit() }

        override fun observe(block: (CloudModel) -> Unit) {
            this.block = block
            dispatcher.doBackground(viewModelScope) {
                cloudDataSource.model { model ->
                    dispatcher.doUi(viewModelScope) {
                        block.invoke(model)
                    }
                }
            }
        }

        override fun observeFirst(block: (CloudModel) -> Unit) {
            this.block = block
            dispatcher.doBackground(viewModelScope) {
                cloudDataSource.modelFirst { model ->
                    dispatcher.doUi(viewModelScope) {
                        block.invoke(model)
                    }
                }
            }
        }

        override fun joinUser(nickname: String) {
            dispatcher.doBackground(viewModelScope) {
                (cloudDataSource as CloudDataSource.Chat.Base).joinUser(nickname)
            }
        }

        override fun disconnect() {
            block = null
            cloudDataSource.disconnect()
        }

    }
}