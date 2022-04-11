package ru.zinoview.viewmodelmemoryleak.chat.ui.core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.navigation.Back
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.navigation.Navigation
import ru.zinoview.viewmodelmemoryleak.chat.data.authentication.AuthenticationRepository
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.Id
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.SharedPreferencesReader
import ru.zinoview.viewmodelmemoryleak.chat.ui.authentication.AuthenticationViewModel
import ru.zinoview.viewmodelmemoryleak.chat.ui.authentication.AuthenticationViewModelFactory
import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.ChatFragment
import ru.zinoview.viewmodelmemoryleak.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Navigation, ToolbarActivity {

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

    private var _binding: ActivityMainBinding? = null
    private val binding by lazy {
        checkNotNull(_binding)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding?.root)

        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            viewModel.auth()
        }
    }

    override fun navigateTo(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .commitNow()

        viewModel.clean()
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun exit() = finish()

    override fun changeTitle(title: String) {
        binding.toolbar.title = title
    }
}