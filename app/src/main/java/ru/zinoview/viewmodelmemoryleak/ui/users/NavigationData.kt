package ru.zinoview.viewmodelmemoryleak.ui.users

import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.NavigationData
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.ParcelableWrapper

interface NavigationData {

    class User(
        parcelable: ParcelableWrapper
    ) : NavigationData.Parcelable(parcelable,KEY){


        private companion object {
            private const val KEY = "user"
        }
    }
}