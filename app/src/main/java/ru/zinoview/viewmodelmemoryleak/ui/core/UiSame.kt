package ru.zinoview.viewmodelmemoryleak.ui.core

interface UiSame : Same<String,Boolean>{

    fun sameId(id: String) : Boolean
}