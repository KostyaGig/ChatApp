package ru.zinoview.viewmodelmemoryleak.ui.di.data.core

import android.content.ContentResolver
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.ui.chat.notification.ShowNotification
import ru.zinoview.viewmodelmemoryleak.data.core.ExceptionMapper
import ru.zinoview.viewmodelmemoryleak.ui.join.Bitmap

class CoreDataModule(
    private val notification: ShowNotification,
    private val contentResolver: ContentResolver
) : ru.zinoview.viewmodelmemoryleak.ui.di.core.Module {
    override fun add(modules: MutableList<Module>) {
        modules.add(coreDataModule)
    }

    private val coreDataModule = module {

        single<ExceptionMapper> {
            ExceptionMapper.Abstract.Base(
                get()
            )
        }

        single<Bitmap> {
            Bitmap.Base(contentResolver)
        }

        single { notification }
    }
}