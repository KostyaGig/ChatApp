package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

interface ListItem<T> {

    fun item(src: List<T>, arg: String) : T
    fun index(src: List<T>,arg: String) : Int

    class Base : ListItem<CloudMessage> {

        override fun item(src: List<CloudMessage>, id: String)
            = src.find{ item -> item.same(id,Unit)  } ?: CloudMessage.Empty

        override fun index(src: List<CloudMessage>, id: String): Int {
            var index = -1
            src.forEachIndexed { i,  message ->
                if (message.same(id,Unit)) {
                    index = i
                    return@forEachIndexed
                }
            }
            return index
        }
    }
}