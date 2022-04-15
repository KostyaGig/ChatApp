package ru.zinoview.viewmodelmemoryleak.data.core.cloud

interface Subscribe<T> {

    fun subscribe(block: (T) -> Unit)
}