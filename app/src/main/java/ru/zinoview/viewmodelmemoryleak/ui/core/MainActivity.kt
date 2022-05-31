package ru.zinoview.viewmodelmemoryleak.ui.core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.getKoin
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.databinding.ActivityMainBinding
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.*
import ru.zinoview.viewmodelmemoryleak.ui.join.ImageResult

class MainActivity : AppCompatActivity(), Navigation, ToolbarActivity, ActivityLauncher {

    private var _binding: ActivityMainBinding? = null
    private val binding by lazy {
        checkNotNull(_binding)
    }

    private val fragmentIntent = getKoin().get<Intent<String>>()

    private val imageLauncher = ActivityResultLauncher.Image(
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            val fragment = supportFragmentManager.fragments.first() as ImageResult
            uri?.let { fragment.onImageResult(it) }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding?.root)

        setSupportActionBar(binding.toolbar)

        if (savedInstanceState != null) {
            fragmentIntent.navigate(this)
        } else {
            fragmentIntent.navigate(intent, this)
        }

    }

    override fun navigateTo(fragment: Fragment, data: NavigationData) {
        fragmentIntent.saveFragment(fragment)
        fragment.arguments = data.bundle()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commitNow()
    }

    override fun back() {
        val fragment = supportFragmentManager.fragments.first() as Back
        fragment.back(this)
    }

    override fun launch(launcherType: LauncherType) = launcherType.launch(imageLauncher)

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun exit() = finish()

    override fun changeTitle(title: String) {
        supportActionBar?.title = title
    }
}