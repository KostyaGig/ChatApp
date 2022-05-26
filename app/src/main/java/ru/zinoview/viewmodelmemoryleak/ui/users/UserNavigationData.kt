package ru.zinoview.viewmodelmemoryleak.ui.users

import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.NavigationData
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.ParcelableWrapper

interface UserNavigationData {

    abstract class Base(
        parcelable: ParcelableWrapper,
    ) : NavigationData.Parcelable(parcelable,KEY) {

        class User(
            parcelable: ParcelableWrapper
        ) : Base(parcelable)

        object Fetch : NavigationData.Parcelable(ParcelableWrapper.Empty, KEY)

        private companion object {
            private const val KEY = "user"
        }
    }

}