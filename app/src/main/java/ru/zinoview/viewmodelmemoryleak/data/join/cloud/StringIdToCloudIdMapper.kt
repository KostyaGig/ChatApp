package ru.zinoview.viewmodelmemoryleak.data.join.cloud

import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.core.Mapper
import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider

interface StringIdToCloudIdMapper : Mapper<String, CloudId> {

    class Base(
        private val resourceProvider: ResourceProvider
    ) : StringIdToCloudIdMapper {

        override fun map(src: String): CloudId {

            return if (src == resourceProvider.string(R.string.nickname_is_empty_text) || src == resourceProvider.string(
                    R.string.something_went_wrong
                )
            ) {
                CloudId.Failure(src)
            } else {
                CloudId.Base(src)
            }
        }
    }
}