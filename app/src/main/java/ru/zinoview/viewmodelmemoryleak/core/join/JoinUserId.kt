package ru.zinoview.viewmodelmemoryleak.core.join

import ru.zinoview.viewmodelmemoryleak.ui.join.ImageProfile

interface JoinUserId<T> {

    fun joinedUserId(image: ImageProfile,nickname: String,block:(T) -> Unit)
}