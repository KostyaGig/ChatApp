package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

interface ListSize {

    fun isLessThen(size: Int,src: List<*>) : Boolean

    class Base : ListSize {

        override fun isLessThen(size: Int, src: List<*>)
            = src.size < size
    }
}