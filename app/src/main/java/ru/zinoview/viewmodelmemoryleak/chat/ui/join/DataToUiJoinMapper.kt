package ru.zinoview.viewmodelmemoryleak.chat.ui.join

import ru.zinoview.viewmodelmemoryleak.chat.core.join.Mapper

class DataToUiJoinMapper : Mapper<UiJoin> {

    override fun map() = UiJoin.Success

    override fun mapFailure() = UiJoin.Failure
}