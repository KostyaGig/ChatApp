package ru.zinoview.viewmodelmemoryleak.chat.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.CloudModel

interface ChatViewModel {

    fun emit()

    fun observe(block: (CloudModel) -> Unit)

    fun disconnect()

    class Base(
        private val cloudDataSource: CloudDataSource,
        private val dispatcher: Dispatcher
    ) : ViewModel(),ChatViewModel {

        override fun emit() = dispatcher.doBackground(viewModelScope) { cloudDataSource.emit() }

        override fun observe(block: (CloudModel) -> Unit) {
            dispatcher.doBackground(viewModelScope) {
                cloudDataSource.model { model ->
                    dispatcher.doUi(viewModelScope) {
                        block.invoke(model)
                    }
                }
            }
        }

        override fun disconnect() = cloudDataSource.disconnect()

    }
}