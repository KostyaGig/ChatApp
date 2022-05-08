package ru.zinoview.viewmodelmemoryleak.domain.connection

import ru.zinoview.viewmodelmemoryleak.core.connection.Connection


interface DataToDomainConnectionMapper: Connection.Mapper<DomainConnection> {
    class Base : DataToDomainConnectionMapper {

        override fun mapSuccess(message: String)
                = DomainConnection.Success(message)

        override fun mapMessage(message: String)
                = DomainConnection.Message(message)
    }
}
