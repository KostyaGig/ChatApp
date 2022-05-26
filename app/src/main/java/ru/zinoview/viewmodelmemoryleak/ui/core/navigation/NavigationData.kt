package ru.zinoview.viewmodelmemoryleak.ui.core.navigation

import android.os.Bundle

interface NavigationData {

    fun bundle() : Bundle = Bundle()

    fun <T : android.os.Parcelable?> parcelable(bundle: Bundle) : ParcelableWrapper = ParcelableWrapper.Empty

    abstract class Parcelable(
        private val parcelable: ParcelableWrapper,
        private val key: String
    ) : NavigationData {

        override fun bundle() =  parcelable.bundle(key)

        override fun <T : android.os.Parcelable?> parcelable(bundle: Bundle)
            = ParcelableWrapper.Base(bundle.getParcelable<T>(key)!!)
    }

}