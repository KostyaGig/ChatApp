package ru.zinoview.viewmodelmemoryleak.ui.connection

import ru.zinoview.viewmodelmemoryleak.core.connection.Connection

interface DataToUiConnectionMapper : Connection.Mapper<UiConnection> {

    class Base : DataToUiConnectionMapper {

        override fun mapSuccess(message: String)
            = UiConnection.Success(message)

        override fun mapMessage(message: String)
            = UiConnection.Message(message)
    }
}
