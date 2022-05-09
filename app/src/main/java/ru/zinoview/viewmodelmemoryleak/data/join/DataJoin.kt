package ru.zinoview.viewmodelmemoryleak.data.join

import ru.zinoview.viewmodelmemoryleak.core.join.Join
import ru.zinoview.viewmodelmemoryleak.core.join.Mapper

interface DataJoin : Join {

    object Success : DataJoin {
        override fun <T> map(mapper: Mapper<T>): T
            = mapper.map()
    }

    data class Failure(
        private val message: String
    ) : DataJoin {
        override fun <T> map(mapper: Mapper<T>): T
            = mapper.mapFailure(message)
    }

    data class Test(
        private val id: String
    ) : DataJoin {

        override fun <T> map(mapper: Mapper<T>): T
            = mapper.map()
    }

}