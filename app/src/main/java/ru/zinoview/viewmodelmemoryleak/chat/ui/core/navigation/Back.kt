package ru.zinoview.viewmodelmemoryleak.chat.ui.core.navigation

interface Back {

    fun back(navigation: Navigation) = Unit

    fun back() = Unit
}