package ru.zinoview.viewmodelmemoryleak.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.koin.android.ext.android.get
import org.koin.android.ext.android.getKoin
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.ui.core.koin_scope.KoinScope
import ru.zinoview.viewmodelmemoryleak.ui.core.koin_scope.ScreenScope
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.*

class  AuthActivity : AppCompatActivity() {

    private val scope = KoinScope.Base()

    private val viewModel by lazy {
        get<AuthenticationViewModel.Base>()
    }

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

        scope.scope(ScreenScope.Auth(),getKoin())

        viewModel.auth()
    }

    override fun onStart() {
        super.onStart()

        viewModel.observe(this) { uiAuth ->
            uiAuth.navigate(navigation)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        scope.clean(getKoin())
    }

}