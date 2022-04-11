package ru.zinoview.viewmodelmemoryleak.chat.ui.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import io.socket.client.IO
import ru.zinoview.viewmodelmemoryleak.abstract_ex.AbstractFragment
import ru.zinoview.viewmodelmemoryleak.chat.core.ResourceProvider
import ru.zinoview.viewmodelmemoryleak.chat.ui.core.navigation.Navigation
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.Id
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.IdSharedPreferences
import ru.zinoview.viewmodelmemoryleak.chat.data.cache.SharedPreferencesReader
import ru.zinoview.viewmodelmemoryleak.chat.data.core.cloud.ActivityConnection
import ru.zinoview.viewmodelmemoryleak.chat.data.core.cloud.Json
import ru.zinoview.viewmodelmemoryleak.chat.data.core.cloud.SocketConnection
import ru.zinoview.viewmodelmemoryleak.chat.data.chat.ChatRepository
import ru.zinoview.viewmodelmemoryleak.chat.data.chat.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.chat.data.chat.CloudToDataMessageMapper
import ru.zinoview.viewmodelmemoryleak.chat.data.chat.cloud.Data
import ru.zinoview.viewmodelmemoryleak.chat.data.chat.cloud.MessagesStore
import ru.zinoview.viewmodelmemoryleak.chat.data.connection.CloudToDataConnectionMapper
import ru.zinoview.viewmodelmemoryleak.chat.data.connection.ConnectionRepository
import ru.zinoview.viewmodelmemoryleak.chat.data.connection.cloud.ConnectionState
import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.edit.EditChatMessageSession
import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.edit.EditMessageListener
import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.edit.UiToEditChatMessageMapper
import ru.zinoview.viewmodelmemoryleak.databinding.ChatFragmentBinding

import ru.zinoview.viewmodelmemoryleak.chat.ui.core.ToolbarActivity
import ru.zinoview.viewmodelmemoryleak.chat.ui.chat.view.ViewWrapper


class ChatFragment : AbstractFragment<ChatViewModel.Base,ChatFragmentBinding>(
    ChatViewModel.Base::class
) {

    private var adapter: ChatAdapter = ChatAdapter.Empty
    private var networkConnectionReceiver: NetworkConnectionReceiver = NetworkConnectionReceiver.Empty

    override fun factory(): ViewModelProvider.Factory {
        val id = Id.Base()
        val prefs = IdSharedPreferences.Base(
            SharedPreferencesReader.Base(id),
            id,
            requireActivity().applicationContext
        )
        val socket = IO.socket("http://10.0.2.2:3000")
        val connection = SocketConnection.Base(
            ActivityConnection.Base()
        )
        return ChatViewModelFactory.Base(
            ChatRepository.Base(
                CloudDataSource.Base(
                    socket,
                    connection,
                    Json.Base(),
                    Gson(),
                    Data.CloudMessage(),
                    MessagesStore.Base()
                ),
                CloudToDataMessageMapper(
                    prefs
                ),
                prefs
            ),
        ConnectionRepository.Base(
            CloudToDataConnectionMapper(),
            ru.zinoview.viewmodelmemoryleak.chat.data.connection.cloud.CloudDataSource.Base(
                socket, connection, ConnectionState.Base(), ResourceProvider.Base(
                    requireActivity().applicationContext
                )
            )
        ))
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        networkConnectionReceiver = NetworkConnectionReceiver.Base(viewModel)

        val diffUtil = ChatMessageDiffUtil()
        val editContainer = ViewWrapper.Base(binding.editMessageContainer)

        val editChatMessageSession = EditChatMessageSession.Base(
            editContainer,
            ViewWrapper.EditText(
                binding.messageField
            )
        )

        adapter = ChatAdapter(diffUtil, object : EditMessageListener {
            override fun edit(message: UiChatMessage) {
                message.show(
                    ViewWrapper.Text(
                        binding.oldMessageTv
                    )
                )
                editChatMessageSession.show(Unit)
                val editMessage = message.map(UiToEditChatMessageMapper())
                editChatMessageSession.addMessage(editMessage)
            }
        })

        binding.cancelEditBtn.setOnClickListener {
            editChatMessageSession.disconnect(Unit)
        }

        binding.chatRv.adapter = adapter

        binding.sendMessageBtn.setOnClickListener {
            val message = binding.messageField.text.toString().trim()
            editChatMessageSession.sendMessage(viewModel,message)

            // todo refactor
//            if (message.isEmpty()) {
//                SnackBar.Base(
//                    binding.messageField,
//                    SnackBar.SnackBarVisibility(
//                        binding.writeMessageContainer,
//                        SnackBarHeight.Base()
//                    )
//                ).show("Enter a message")
//            } else {
//                viewModel.doAction(message)
//            }
        }


        viewModel.messages()
        viewModel.connection()
    }

    override fun onStart() {
        super.onStart()
        networkConnectionReceiver.register(requireActivity().applicationContext)
        viewModel.observe(this) { messages ->
            messages.last().changeTitle(requireActivity() as ToolbarActivity)
            adapter.submitList(ArrayList(messages))
        }
        viewModel.observeConnection(this) { connection ->
            connection.changeTitle(requireActivity() as ToolbarActivity)
            connection.showError(adapter)
            connection.messages(viewModel)
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.clean()
        networkConnectionReceiver.unRegister(requireActivity().applicationContext)
    }

    override fun back(navigation: Navigation) = navigation.exit()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): ChatFragmentBinding
        = ChatFragmentBinding.inflate(layoutInflater,container,false)
}