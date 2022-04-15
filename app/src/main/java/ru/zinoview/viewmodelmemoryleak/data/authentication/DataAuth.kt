package ru.zinoview.viewmodelmemoryleak.data.authentication

import ru.zinoview.viewmodelmemoryleak.core.authentication.Auth
import ru.zinoview.viewmodelmemoryleak.core.authentication.Mapper

interface DataAuth : Auth {

    object Success : DataAuth {
        override fun <T> map(mapper: Mapper<T>): T
            = mapper.map()
    }

    object Failure: DataAuth {
        override fun <T> map(mapper: Mapper<T>): T
            = mapper.mapFailure()
    }
}