package ru.zinoview.viewmodelmemoryleak.data.join.cloud

import ru.zinoview.viewmodelmemoryleak.core.cloud.SocketData
import ru.zinoview.viewmodelmemoryleak.core.cloud.SocketWrapper
import ru.zinoview.viewmodelmemoryleak.core.join.JoinUserId
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.AbstractCloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Disconnect
import ru.zinoview.viewmodelmemoryleak.data.core.cloud.Json
import ru.zinoview.viewmodelmemoryleak.ui.join.Base64Image
import ru.zinoview.viewmodelmemoryleak.ui.join.ImageProfile

interface CloudDataSource : Disconnect<Unit>, AbstractCloudDataSource, JoinUserId<CloudId> {

    class Base(
        private val socketWrapper: SocketWrapper,
        private val base64Image: Base64Image,
        private val json: Json,
        private val mapper: StringIdToCloudIdMapper
    ) : AbstractCloudDataSource.Base(socketWrapper), CloudDataSource {

        override fun joinedUserId(image: ImageProfile, nickname: String, block: (CloudId) -> Unit) {
            socketWrapper.subscribe(JOIN_USER) { data ->
                val cloudId = mapper.map(data.first() as String)
                block.invoke(cloudId)
            }

            val base64Image = image.base64Image(base64Image)

            val user = SocketData.Base(
                json.json(
                    Pair(
                        IMAGE_KEY,
                        base64Image
                    ),
                    Pair(
                        NICKNAME_KEY,
                        nickname
                    )
                )
            )

            socketWrapper.emit(JOIN_USER, user)
        }
    }

    private companion object {
        private const val JOIN_USER = "join_user"

        private const val NICKNAME_KEY = "nickname"
        private const val IMAGE_KEY = "image"
    }
}
