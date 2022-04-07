package ru.zinoview.viewmodelmemoryleak.chat.ui.join

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import io.socket.client.IO
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.abstract_ex.AbstractFragment
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.SharedPreferencesReader
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.Connect
import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.ChatFragment
import ru.zinoview.viewmodelmemoryleak.databinding.JoinFragmentBinding

class JoinUserFragment : AbstractFragment<JoinUserViewModel.Base,JoinFragmentBinding>(
    JoinUserViewModel.Base::class
) {

    override fun factory(): ViewModelProvider.Factory
        = JoinUserViewModelFactory.Base(
        CloudDataSource.JoinUser.Base(
            IO.socket("http://10.0.2.2:3000"),
            Connect.Base(),
            IdSharedPreferences.Base(
                SharedPreferencesReader.Base(),
                requireContext()
            )
        )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.joinBtn.setOnClickListener {
            val nickname = binding.nicknameField.text.toString().trim()
            viewModel.joinUser(nickname) {
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, ChatFragment())
                    .commit()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.disconnect()
    }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): JoinFragmentBinding
        = JoinFragmentBinding.inflate(inflater,container,false)
}