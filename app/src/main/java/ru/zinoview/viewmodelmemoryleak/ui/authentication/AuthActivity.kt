package ru.zinoview.viewmodelmemoryleak.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.koin.android.ext.android.get
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.*

class AuthActivity : AppCompatActivity() {

    private val viewModel = get<AuthenticationViewModel.Base>()

    private val navigation by lazy {
        ActivityNavigation.Base(
            get(),
            get(),
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