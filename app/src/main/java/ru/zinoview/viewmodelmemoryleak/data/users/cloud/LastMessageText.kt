package ru.zinoview.viewmodelmemoryleak.data.users.cloud

import ru.zinoview.viewmodelmemoryleak.core.Mapper

interface LastMessageText : Mapper<CloudLastMessage,String> {


    class Base(
    ) : LastMessageText {

        override fun map(src: CloudLastMessage)
            = src.map(Unit)
    }
}