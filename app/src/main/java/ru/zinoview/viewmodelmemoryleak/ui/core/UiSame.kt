package ru.zinoview.viewmodelmemoryleak.ui.core

interface UiSame : Same<String>{

    fun sameId(id: String) : Boolean
}