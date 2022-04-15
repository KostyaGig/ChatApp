package ru.zinoview.viewmodelmemoryleak.ui.join

import ru.zinoview.viewmodelmemoryleak.core.join.Mapper

class DataToUiJoinMapper : Mapper<UiJoin> {

    override fun map() = UiJoin.Success

    override fun map(message: String) = UiJoin.Failure(message)
}