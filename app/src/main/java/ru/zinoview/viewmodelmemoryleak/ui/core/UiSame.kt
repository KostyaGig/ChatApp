package ru.zinoview.viewmodelmemoryleak.ui.core

interface UiSame : Same<String,Boolean>, SameOne<String> {

    fun sameFound(isFounded: Boolean) : Boolean
}