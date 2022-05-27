package ru.zinoview.viewmodelmemoryleak.data.join.cloud

import ru.zinoview.viewmodelmemoryleak.core.Mapper
import ru.zinoview.viewmodelmemoryleak.core.Save
import ru.zinoview.viewmodelmemoryleak.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.data.join.CloudIdToDataJoinMapper
import ru.zinoview.viewmodelmemoryleak.data.join.DataJoin

interface CloudId : Save<IdSharedPreferences<String, Unit>>, Mapper<CloudIdToDataJoinMapper,DataJoin> {

    override fun save(data: IdSharedPreferences<String, Unit>) = Unit

    class Base(
        private val id: String
    ) : CloudId {
        override fun save(prefs: IdSharedPreferences<String, Unit>)
            = prefs.save(id)

        override fun map(mapper: CloudIdToDataJoinMapper) = mapper.map(Unit)

    }

    class Failure(
        private val message: String
    ) : CloudId {

        override fun map(mapper: CloudIdToDataJoinMapper)  = mapper.mapFailure(message)
    }
}