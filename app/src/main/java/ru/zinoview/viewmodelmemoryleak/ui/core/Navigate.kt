package ru.zinoview.viewmodelmemoryleak.ui.core


interface Navigate<T> {

    fun navigate(navigation: T)
}