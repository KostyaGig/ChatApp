package ru.zinoview.viewmodelmemoryleak.abstract_ex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import ru.zinoview.viewmodelmemoryleak.R

class TapActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tap)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,TapFragment())
            .commit()


    }
}