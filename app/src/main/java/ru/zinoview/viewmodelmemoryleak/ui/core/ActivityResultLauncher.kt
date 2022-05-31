package ru.zinoview.viewmodelmemoryleak.ui.core

import androidx.activity.result.ActivityResultLauncher

interface ActivityResultLauncher : Launch<Unit> {

    class Image(
        private val launcher: ActivityResultLauncher<String>
    ) : ru.zinoview.viewmodelmemoryleak.ui.core.ActivityResultLauncher {

        override fun launch(arg: Unit) = launcher.launch(TYPE)

        private companion object {
            private const val TYPE = "image/*"
        }
    }
}