package ru.zinoview.viewmodelmemoryleak

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import ru.zinoview.viewmodelmemoryleak.ui.core.Communication

interface Work<T> {

    fun execute(scope: CoroutineScope,background: suspend () -> T, ui:(T) -> Unit)

    class Base(
        private val dispatcher: Dispatcher
    ) : Work<String> {

        override fun execute(scope: CoroutineScope, background: suspend () -> String, ui: (String) -> Unit) {
            dispatcher.doBackground(scope) {
                val result = background.invoke()
                dispatcher.doUi(scope) {
                    ui.invoke(result)
                }
            }
        }

    }
}

interface Dispatcher {

    fun doBackground(scope: CoroutineScope,block: suspend () -> Unit)

    fun doUi(scope: CoroutineScope,block: suspend () -> Unit)

    class Base(
        private val dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) : Dispatcher {

        override fun doBackground(scope: CoroutineScope, block: suspend () -> Unit) {
            scope.launch(dispatcher) {
                block.invoke()
            }
        }

        override fun doUi(scope: CoroutineScope,block: suspend () -> Unit) {
            scope.launch(Dispatchers.Main) {
                block.invoke()
            }
        }
    }
}

interface WorkInteractor {

    fun string() : String
}

interface WorkViewModel {

    fun string()

    class Base(
        private val workInteractor: WorkInteractor,
        private val work: Work<String>,
        private val communication: Communication<String>
    ) : WorkViewModel, ViewModel() {

        override fun string() {
            work.execute(viewModelScope,{
                workInteractor.string()
            }, { string ->
                communication.postValue(string)
            })
        }
    }
}