package ru.zinoview.viewmodelmemoryleak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import ru.zinoview.viewmodelmemoryleak.memory_leak.LeakViewModel
import kotlin.concurrent.thread

class LeakActivity : AppCompatActivity() {

    private var viewModel: LeakViewModel = LeakViewModel.Empty()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leak)

        viewModel = ViewModelProvider(this)[LeakViewModel.Base::class.java]
        viewModel.addView(this)

        thread {
            Thread.sleep(1000)
            viewModel.print()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.clear()
    }

}