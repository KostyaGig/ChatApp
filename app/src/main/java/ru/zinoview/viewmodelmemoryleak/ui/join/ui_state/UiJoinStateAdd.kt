package ru.zinoview.viewmodelmemoryleak.ui.join.ui_state

import ru.zinoview.viewmodelmemoryleak.core.Add
import ru.zinoview.viewmodelmemoryleak.ui.join.ImageProfile

interface UiJoinStateAdd : Add<String> {

    fun addImage(image: ImageProfile)
}