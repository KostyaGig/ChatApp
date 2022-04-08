package ru.zinoview.viewmodelmemoryleak.chat.core.navigation

interface Back {

    fun back(navigation: Navigation) = Unit

    fun back() = Unit
}