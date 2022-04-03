package ru.zinoview.viewmodelmemoryleak.chat.ui

import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Observable
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.Observer

class UiChatMessagesObservable : Observable<List<UiChatMessage>> {

    private val observers = mutableListOf<Observer<List<UiChatMessage>>>()
    private val messages = mutableListOf<UiChatMessage>()

    override fun observe(observer: Observer<List<UiChatMessage>>) {
        observers.add(observer)
    }

    override fun add(message: List<UiChatMessage>) {
        messages.add(message[0])
        observers.forEach { observer ->
            observer.update(messages)
        }
    }

}