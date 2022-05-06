package ru.zinoview.viewmodelmemoryleak.core.cloud

interface Subscribe {

    fun subscribe(branch: String,block:(Array<Any>) -> Unit)
}