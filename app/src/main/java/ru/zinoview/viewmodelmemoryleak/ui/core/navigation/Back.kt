package ru.zinoview.viewmodelmemoryleak.ui.core.navigation

interface Back {

    fun back(navigation: Navigation) = Unit

    fun back() = Unit
}