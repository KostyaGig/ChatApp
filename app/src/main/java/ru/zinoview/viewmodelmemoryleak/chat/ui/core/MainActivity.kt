package ru.zinoview.viewmodelmemoryleak.chat.ui.core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.chat.core.navigation.Back
import ru.zinoview.viewmodelmemoryleak.chat.core.navigation.Navigation
import ru.zinoview.viewmodelmemoryleak.chat.data.authentication.AuthenticationRepository
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.Id
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.SharedPreferencesReader
import ru.zinoview.viewmodelmemoryleak.chat.ui.authentication.AuthenticationViewModel
import ru.zinoview.viewmodelmemoryleak.chat.ui.authentication.AuthenticationViewModelFactory

class MainActivity : AppCompatActivity(), Navigation {

    private val viewModel by lazy {
        val id = Id.Base()
        ViewModelProvider(
            this,
            AuthenticationViewModelFactory.Base(
                AuthenticationRepository.Base(
                    IdSharedPreferences.Base(
                        SharedPreferencesReader.Base(
                            id,
                        ),
                        id,
                        this
                    )
                )
            )
        )[AuthenticationViewModel.Base::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.auth()
    }

    override fun navigateTo(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .commit()
    }

    override fun onStart() {
        super.onStart()

        viewModel.observe(this) { uiAuth ->
            uiAuth.navigate(this)
        }
    }

    override fun back() {
        val fragment = supportFragmentManager.fragments.first() as Back
        fragment.back(this)
    }

    override fun exit() = finish()
}