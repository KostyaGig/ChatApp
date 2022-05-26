package ru.zinoview.viewmodelmemoryleak.ui.core.navigation

import android.os.Bundle
import android.os.Parcelable
import java.lang.IllegalStateException

interface ParcelableWrapper {

    fun bundle(key: String) : Bundle = Bundle()

    fun <T : Parcelable> parcelable() : T

    class Base(
        private val parcelable: Parcelable
    ) : ParcelableWrapper {

        override fun bundle(key: String): Bundle
            = Bundle().apply {
                putParcelable(key,parcelable)
            }

        override fun <T : Parcelable> parcelable() = parcelable as T
    }

    object Empty : ParcelableWrapper {
        override fun <T : Parcelable> parcelable() = throw IllegalStateException("ParcelableWrapper.Empty throw Exception")
    }
}