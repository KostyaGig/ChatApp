package ru.zinoview.viewmodelmemoryleak.ui.join

import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.ViewWrapper
import ru.zinoview.viewmodelmemoryleak.ui.core.Show

interface ImageProfile : ru.zinoview.viewmodelmemoryleak.core.join.Base64Image<Base64Image>, Show<ViewWrapper> {

    override fun show(arg: ViewWrapper) = Unit

    data class Uri(
        private val uri: android.net.Uri
    ) : ImageProfile {

        override fun base64Image(param: Base64Image)
            = param.base64Image(uri)
    }

    data class Bitmap(
        private val bitmap: android.graphics.Bitmap
    ) : ImageProfile {

        override fun base64Image(param: Base64Image) = ""
        override fun show(image: ViewWrapper) = image.showImage(bitmap)
    }

    class Drawable(
        private val drawable: android.graphics.drawable.Drawable
    ) : ImageProfile {

        override fun base64Image(param: Base64Image): String {
            return param.base64Image(drawable)
        }
    }

    object Default : ImageProfile {

        override fun base64Image(base64Image: Base64Image)
            = base64Image.base64Image(R.drawable.profile_default_image)
    }

    object Empty : ImageProfile {
        override fun base64Image(base64Image: Base64Image)
            = ""

        override fun show(image: ViewWrapper) = image.showImage(R.drawable.ic_camera)
    }
}