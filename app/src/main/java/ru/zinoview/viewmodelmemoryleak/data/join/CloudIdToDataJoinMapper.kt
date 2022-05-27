package ru.zinoview.viewmodelmemoryleak.data.join

import ru.zinoview.viewmodelmemoryleak.core.FailureMapper
import ru.zinoview.viewmodelmemoryleak.core.Mapper

interface CloudIdToDataJoinMapper : Mapper<Unit,DataJoin>, FailureMapper.String<DataJoin> {

    class Base : CloudIdToDataJoinMapper {

        override fun map(src: Unit) = DataJoin.Success

        override fun mapFailure(message: String) = DataJoin.Failure(message)
    }
}