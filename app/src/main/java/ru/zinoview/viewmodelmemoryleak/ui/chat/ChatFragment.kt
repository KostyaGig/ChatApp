package ru.zinoview.viewmodelmemoryleak.ui.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.getKoin
import ru.zinoview.viewmodelmemoryleak.databinding.ChatFragmentBinding
import ru.zinoview.viewmodelmemoryleak.ui.chat.edit.EditMessageListener
import ru.zinoview.viewmodelmemoryleak.ui.chat.edit.MessageSession
import ru.zinoview.viewmodelmemoryleak.ui.chat.edit.ToEditedMessageMapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.ToOldMessageMapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.UiState
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.UiStateViewModel
import ru.zinoview.viewmodelmemoryleak.ui.chat.user_status.UserStatusViewModel
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.SnackBar
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.SnackBarHeight
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.ViewWrapper

import ru.zinoview.viewmodelmemoryleak.ui.core.ToolbarActivity
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.Navigation
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.NetworkConnectionFragment


class ChatFragment : NetworkConnectionFragment<ChatViewModel.Base, ChatFragmentBinding>(
    ChatViewModel.Base::class
) {

    // todo move to interface
    fun showNotificationMessageInRecyclerView(messageId: String) {
        Toast.makeText(requireContext(),messageId,Toast.LENGTH_LONG).show()
        Log.d("zinoviewk","SHOW NOTIF MSG IN rec view $messageId")
    }

    private var adapter: ChatAdapter = ChatAdapter.Empty
    private var scrollListener: ChatRecyclerViewScrollListener = ChatRecyclerViewScrollListener.Empty

    private var messageSession: MessageSession = MessageSession.Empty

    private val uiStateViewModel by lazy {
        getKoin().get<UiStateViewModel.Base>()
    }

    private val userStatusViewModel by lazy {
        getKoin().get<UserStatusViewModel.Base>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            uiStateViewModel.read(Unit)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        networkConnectionReceiver = NetworkConnectionReceiver.Base(viewModel)

        val editContainer = ViewWrapper.Base(binding.editMessageContainer)

        val snackBar = SnackBar.EmptyField(
            binding.messageField, SnackBar.SnackBarVisibility(
                binding.writeMessageContainer,
                SnackBarHeight.Base()
            )
        )

        messageSession = MessageSession.Base(
            editContainer,
            ViewWrapper.EditText(
                binding.messageField
            ),
            snackBar,
            ToEditedMessageMapper(),
            ToOldMessageMapper()
        )

        val diffUtil = ChatMessageDiffUtil()
        adapter = ChatAdapter(diffUtil, object : EditMessageListener {
            override fun edit(message: UiMessage) {
                val text = ViewWrapper.Text(binding.oldMessageTv)
                message.show(text)

                messageSession.show(Unit)
                messageSession.add(message)
            }
        })

        val manager = LinearLayoutManager(requireContext())
        binding.chatRv.layoutManager = manager
        binding.chatRv.adapter = adapter

        scrollListener = ChatRecyclerViewScrollListener.Base(manager, viewModel)
        binding.chatRv.addOnScrollListener(scrollListener)

        binding.cancelEditBtn.setOnClickListener {
            messageSession.disconnect(Unit)
        }

        binding.sendMessageBtn.setOnClickListener {
            val message = binding.messageField.text.toString().trim()
            messageSession.sendMessage(viewModel,message)
        }

        viewModel.messages()
        viewModel.connection()
    }


    override fun onStart() {
        super.onStart()
        userStatusViewModel.online()

        viewModel.observe(this) { messages ->
            messages.last().changeTitle(requireActivity() as ToolbarActivity)
            adapter.submitList(messages)
        }

        viewModel.observeScrollCommunication(this) { uiScroll ->
            uiScroll.scroll(binding.chatRv,scrollListener)
        }

        viewModel.observeConnection(this) { connection ->
            connection.changeTitle(requireActivity() as ToolbarActivity)
            connection.showError(adapter)
            connection.messages(viewModel)
        }


        val editText = ViewWrapper.EditText(binding.messageField)
        val text = ViewWrapper.Text(binding.oldMessageTv)

        uiStateViewModel.observe(this) { states ->
            states.forEach { state ->
                state.recover(editText,text,messageSession,adapter)
            }
        }
    }

    override fun onPause() {
        super.onPause()

        val editTextState = UiState.EditText(binding.messageField.text.toString())
        messageSession.saveState(uiStateViewModel,editTextState)

        viewModel.showProcessingMessages()
        userStatusViewModel.offline()
        userStatusViewModel.clean()
    }

    override fun back(navigation: Navigation) = navigation.exit()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): ChatFragmentBinding
        = ChatFragmentBinding.inflate(layoutInflater,container,false)

    override fun dependenciesScope() = SCOPE_NAME

    private companion object {
        private const val SCOPE_NAME = "cufScope"
    }
}