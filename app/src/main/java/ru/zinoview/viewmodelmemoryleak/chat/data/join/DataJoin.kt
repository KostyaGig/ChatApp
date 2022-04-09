package ru.zinoview.viewmodelmemoryleak.chat.data.join

import ru.zinoview.viewmodelmemoryleak.chat.core.join.Join
import ru.zinoview.viewmodelmemoryleak.chat.core.join.Mapper

interface DataJoin : Join {

    object Success : DataJoin {
        override fun <T> map(mapper: Mapper<T>): T
            = mapper.map()
    }

    class Failure(
        private val message: String
    ) : DataJoin {
        override fun <T> map(mapper: Mapper<T>): T
            = mapper.map(message)
    }

}