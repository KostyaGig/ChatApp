package ru.zinoview.viewmodelmemoryleak.core.join

import ru.zinoview.viewmodelmemoryleak.ui.join.ImageProfile

interface JoinUserId<T> {

    suspend fun joinedUserId(image: ImageProfile,nickname: String) : T
}