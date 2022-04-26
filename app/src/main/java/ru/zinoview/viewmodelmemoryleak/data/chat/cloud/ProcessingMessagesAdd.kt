package ru.zinoview.viewmodelmemoryleak.data.chat.cloud


interface ProcessingMessagesAdd {

    fun add(src: MutableList<CloudMessage>, message: CloudMessage)

    class Base : ProcessingMessagesAdd {

        override fun add(src: MutableList<CloudMessage>, message: CloudMessage) {
            if (message is CloudMessage.Progress) {
              src.add(message)
            }
        }
    }
}