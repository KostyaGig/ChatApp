package ru.zinoview.viewmodelmemoryleak.chat.data.core.cloud

interface Subscribe<T> {

    fun subscribe(block: (T) -> Unit)
}