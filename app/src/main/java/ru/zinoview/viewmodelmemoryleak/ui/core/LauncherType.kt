package ru.zinoview.viewmodelmemoryleak.ui.core

interface LauncherType : Launch<ActivityResultLauncher> {

    object Image : LauncherType {

        override fun launch(launcher: ActivityResultLauncher)
            = launcher.launch(Unit)
    }
}