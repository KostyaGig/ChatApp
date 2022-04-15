package ru.zinoview.viewmodelmemoryleak.data.core

import ru.zinoview.viewmodelmemoryleak.core.Clean
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Disconnect

abstract class CleanRepository(
    private val disconnect: Disconnect<Unit>
) : Clean {

    override fun clean() = disconnect.disconnect(Unit)
}