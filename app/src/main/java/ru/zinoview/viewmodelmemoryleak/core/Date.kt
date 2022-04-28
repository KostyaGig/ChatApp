package ru.zinoview.viewmodelmemoryleak.core

import java.util.*

interface Date {

    fun date(time: String) : String

    class Notification : Date {

        override fun date(time: String): String {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = time.toLong()
            calendar.get(Calendar.HOUR)
            val typeHalfOfDay = if (calendar.get(Calendar.AM_PM) == Calendar.AM) AM else PM
            return "At ${calendar.get(Calendar.HOUR)}:${calendar.get(Calendar.MINUTE)} $typeHalfOfDay"
        }

        private companion object {
            private const val AM = "am"
            private const val PM = "pm"
        }
    }
}