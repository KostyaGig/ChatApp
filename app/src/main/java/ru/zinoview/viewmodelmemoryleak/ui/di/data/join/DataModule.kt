package ru.zinoview.viewmodelmemoryleak.ui.di.data.join

import android.content.Context
import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.core.ResourceProvider
import ru.zinoview.viewmodelmemoryleak.data.core.ExceptionMapper
import ru.zinoview.viewmodelmemoryleak.data.join.CloudIdToDataJoinMapper
import ru.zinoview.viewmodelmemoryleak.data.join.JoinUserRepository
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module
import ru.zinoview.viewmodelmemoryleak.ui.join.Base64Image
import java.io.ByteArrayOutputStream
import kotlin.math.sign

class DataModule(
    private val context: Context
) : Module {

    private val dataModule = module {

        single<JoinUserRepository> {
            JoinUserRepository.Base(
                get(),
                get(),
                get(),
                ExceptionMapper.Abstract.Join(get()),
                CloudIdToDataJoinMapper.Base()
            )
        }

        single<Base64Image> {
            Base64Image.Base(
                ResourceProvider.Base(context),context.contentResolver, ByteArrayOutputStream()
            )
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(dataModule)
    }
}