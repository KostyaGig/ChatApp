package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

import ru.zinoview.viewmodelmemoryleak.core.IsNotEmpty

interface Filter<T> {

    fun filtered(src: List<T>, coming: List<T>) : List<T>

    class ProcessingMessages(
        private val isNotEmpty: IsNotEmpty<List<CloudMessage>>
    ) : Filter<CloudMessage> {

        override fun filtered(
            src: List<CloudMessage>,
            coming: List<CloudMessage>
        ): List<CloudMessage> {
            if (isNotEmpty.isNotEmpty(src)) {
                val indexes = mutableListOf<Int>()

                src.forEachIndexed { index, srcMessage ->
                    coming.forEach { comingMessage ->

                        if (srcMessage.same(comingMessage)) {
                            indexes.add(index)
                        }
                    }
                }

                val newList = ArrayList(src)

                indexes.forEach { index ->
                    if (newList.size - 1 >= index) {
                        if (newList[index] != null) {
                            newList.removeAt(index)
                        }
                    }
                }

                return newList
            } else {
                return emptyList()
            }
        }
    }
}