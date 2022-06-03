package ru.zinoview.viewmodelmemoryleak.ui.join

import android.util.Log
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.core.Mapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.ViewWrapper
import ru.zinoview.viewmodelmemoryleak.ui.core.Show

interface ImageProfile : ru.zinoview.viewmodelmemoryleak.core.join.Base64Image<Base64Image>, Show<ViewWrapper>, Mapper<Unit,ImageProfile> {

    override fun show(arg: ViewWrapper) = Unit
    override fun map(src: Unit): ImageProfile = Empty

    data class Uri(
        private val uri: android.net.Uri
    ) : ImageProfile {

        override fun base64Image(param: Base64Image) = param.base64Image(uri)
    }

    data class Bitmap(
        private val bitmap: android.graphics.Bitmap
    ) : ImageProfile {

        override fun base64Image(param: Base64Image) = ""
        override fun show(image: ViewWrapper) = image.showBitmap(bitmap)
        override fun map(src: Unit) = Bitmap(bitmap)
    }

    class Drawable(
        private val drawable: android.graphics.drawable.Drawable
    ) : ImageProfile {

        override fun base64Image(param: Base64Image): String {

            val stringImage = param.base64Image(drawable)

            Log.d("zinoviewk","string drawable $stringImage")
            return stringImage
        }
    }

    object Default : ImageProfile {

        override fun base64Image(base64Image: Base64Image)
            = base64Image.base64Image(R.drawable.profile_default_image)
    }

    object Empty : ImageProfile {
        override fun base64Image(base64Image: Base64Image) = ""
        override fun show(image: ViewWrapper) = image.map(R.drawable.ic_camera)
    }
}