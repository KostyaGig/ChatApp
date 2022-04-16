package ru.zinoview.viewmodelmemoryleak.ui.core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.databinding.ActivityMainBinding
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.*

class MainActivity : AppCompatActivity(), Navigation, ToolbarActivity {

    private var _binding: ActivityMainBinding? = null
    private val binding by lazy {
        checkNotNull(_binding)
    }

    private val fragmentIntent by lazy {
        Intent.Fragment(
            ExtraToTypeFragmentMapper.Base(
                StringFragment.Base()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding?.root)

        setSupportActionBar(binding.toolbar)
        fragmentIntent.navigate(intent,this)
    }


    override fun navigateTo(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .commitNow()
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
        supportActionBar?.title = title
    }
}