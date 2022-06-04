package ru.zinoview.viewmodelmemoryleak.ui.core.navigation

import android.os.Bundle
import ru.zinoview.viewmodelmemoryleak.core.Mapper

interface NavigationData : Mapper<Unit,Bundle> {

    override fun map(src: Unit): Bundle = Bundle()

    fun put(src: Bundle) = Unit

    fun <T : android.os.Parcelable?> parcelable(bundle: Bundle) : ParcelableWrapper = ParcelableWrapper.Empty

    abstract class Parcelable(
        private val parcelable: ParcelableWrapper,
        private val key: String
    ) : NavigationData {

        override fun map(src: Unit) =  parcelable.bundle(key)

        override fun put(src: Bundle) = parcelable.put(src,key)

        override fun <T : android.os.Parcelable?> parcelable(bundle: Bundle): ParcelableWrapper {
            return if (bundle.getParcelable<T>(key) == null) {
                ParcelableWrapper.Empty
            } else {
                ParcelableWrapper.Base(bundle.getParcelable<T>(key)!!)
            }
        }
    }

    object Empty : NavigationData
}