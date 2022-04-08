package ru.zinoview.viewmodelmemoryleak.chat.core

import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.Disconnect

abstract class CleanRepository(
    private val disconnect: Disconnect<Unit>
) : Clean {

    override fun clean() = disconnect.disconnect(Unit)
}