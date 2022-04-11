package ru.zinoview.viewmodelmemoryleak.chat.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.chat.data.authentication.AuthenticationRepository
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.Id
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.SharedPreferencesReader
import ru.zinoview.viewmodelmemoryleak.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

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

    private var _binding: ActivityAuthBinding? = null
    private val binding by lazy {
        checkNotNull(_binding)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // todo move all the code associated with checking auth from MainActivity to here

        viewModel.auth()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onStart() {
        super.onStart()

        viewModel.observe(this) { uiAuth ->
//            uiAuth.navigate(this)
        }

    }
}