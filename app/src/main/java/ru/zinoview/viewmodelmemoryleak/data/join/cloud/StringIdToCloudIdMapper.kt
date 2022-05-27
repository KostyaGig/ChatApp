package ru.zinoview.viewmodelmemoryleak.data.join.cloud

import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.core.Mapper
import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider

interface StringIdToCloudIdMapper : Mapper<String, CloudId> {

    class Base(
        private val resourceProvider: ResourceProvider
    ) : StringIdToCloudIdMapper {

        override fun map(id: String) = when (id) {
            EMPTY_ID -> CloudId.Failure(resourceProvider.string(R.string.nickname_is_empty_text))
            else -> CloudId.Base(id)
        }
    }

    private companion object {
        private const val EMPTY_ID = "-1"
    }
}