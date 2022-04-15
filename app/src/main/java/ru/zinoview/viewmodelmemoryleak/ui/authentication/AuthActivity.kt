package ru.zinoview.viewmodelmemoryleak.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.data.authentication.AuthenticationRepository
import ru.zinoview.viewmodelmemoryleak.data.cache.Id
import ru.zinoview.viewmodelmemoryleak.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.data.cache.SharedPreferencesReader
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.*

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

    private val navigation by lazy {
        val stringFragment = StringFragment.Base()
        ActivityNavigation.Base(
            TypeFragmentToExtraMapper.Base(
                stringFragment
            ),
            Intent.Fragment(
                ExtraToTypeFragmentMapper.Base(
                    stringFragment
                )
            ),
            this
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        viewModel.auth()
    }

    override fun onStart() {
        super.onStart()

        viewModel.observe(this) { uiAuth ->
            uiAuth.navigate(navigation)
        }

    }
}