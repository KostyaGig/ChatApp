package ru.zinoview.viewmodelmemoryleak.chat.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import io.socket.client.IO
import ru.zinoview.viewmodelmemoryleak.R
import ru.zinoview.viewmodelmemoryleak.abstract_ex.AbstractFragment
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.SharedPreferencesReader
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.Json
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.SocketConnection
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.chat.ChatRepository
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.chat.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.chat.data.cloud.chat.CloudToDataMessageMapper
import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.view.MessageField
import ru.zinoview.viewmodelmemoryleak.databinding.ChatFragmentBinding

class ChatFragment : AbstractFragment<ChatViewModel.Base,ChatFragmentBinding>(
    ChatViewModel.Base::class
) {

    private var adapter: ChatAdapter = ChatAdapter.Empty

    override fun factory(): ViewModelProvider.Factory {
        val prefs = IdSharedPreferences.Base(
            SharedPreferencesReader.Base(),
            requireContext()
        )
        return ChatViewModelFactory.Base(
            ChatRepository.Base(
                CloudDataSource.Base(
                    IO.socket("http://10.0.2.2:3000"),
                    SocketConnection.Base(),
                    Json.Base(),
                    Gson()
                ),
                CloudToDataMessageMapper(
                    prefs
                ),
                prefs
            ))
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val diffUtil = ChatMessageDiffUtil()
        adapter = ChatAdapter(diffUtil)

        binding.chatRv.adapter = adapter

        binding.sendMessageBtn.setOnClickListener {
            val field = view.findViewById<MessageField.Base>(R.id.message_field)
            field.send(viewModel)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.observe(this) { messages ->
            adapter.submitList(ArrayList(messages))
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.clean()
    }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): ChatFragmentBinding
        = ChatFragmentBinding.inflate(layoutInflater,container,false)
}