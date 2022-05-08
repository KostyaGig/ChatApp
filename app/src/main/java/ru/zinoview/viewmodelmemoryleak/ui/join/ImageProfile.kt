package ru.zinoview.viewmodelmemoryleak.ui.join

import android.net.Uri
import ru.zinoview.viewmodelmemoryleak.R

interface ImageProfile : ru.zinoview.viewmodelmemoryleak.core.join.Base64Image<Base64Image> {

    data class Base(
        private val uri: Uri
    ) : ImageProfile {

        override fun base64Image(param: Base64Image)
            = param.base64Image(uri)
    }

    object Empty : ImageProfile {

        override fun base64Image(base64Image: Base64Image)
            = base64Image.base64Image(R.drawable.ic_camera)
    }
}