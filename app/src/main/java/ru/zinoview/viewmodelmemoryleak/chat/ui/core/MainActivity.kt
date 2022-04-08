package ru.zinoview.viewmodelmemoryleak.chat.ui.core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.chat.ui.join.JoinUserFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,JoinUserFragment())
            .commit()

    }


}