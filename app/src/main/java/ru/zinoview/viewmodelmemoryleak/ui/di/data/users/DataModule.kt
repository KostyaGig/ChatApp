package ru.zinoview.viewmodelmemoryleak.ui.di.data.users

import android.content.ContentResolver
import android.graphics.Bitmap
import org.koin.dsl.module.module
import ru.zinoview.viewmodelmemoryleak.data.core.ExceptionMapper
import ru.zinoview.viewmodelmemoryleak.data.join.JoinUserRepository
import ru.zinoview.viewmodelmemoryleak.data.users.CloudToAbstractUserMapper
import ru.zinoview.viewmodelmemoryleak.data.users.UsersRepository
import ru.zinoview.viewmodelmemoryleak.ui.di.core.Module

class DataModule(
    private val contentResolver: ContentResolver
) : Module {

    private val dataModule = module {

        single<UsersRepository> {
            UsersRepository.Base(
                get(),
                CloudToAbstractUserMapper.Base(),
                ru.zinoview.viewmodelmemoryleak.ui.join.Bitmap.Base(
                    contentResolver
                ),
                ExceptionMapper.Abstract.User(get()),
                get()
            )
        }
    }

    override fun add(modules: MutableList<org.koin.dsl.module.Module>) {
        modules.add(dataModule)
    }
}