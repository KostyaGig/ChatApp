package ru.zinoview.viewmodelmemoryleak.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.getKoin
import ru.zinoview.viewmodelmemoryleak.databinding.ChatFragmentBinding
import ru.zinoview.viewmodelmemoryleak.ui.chat.edit.EditMessageListener
import ru.zinoview.viewmodelmemoryleak.ui.chat.edit.MessageSession
import ru.zinoview.viewmodelmemoryleak.ui.chat.edit.UiToEditChatMessageMapper
import ru.zinoview.viewmodelmemoryleak.ui.chat.state.UiState
import ru.zinoview.viewmodelmemoryleak.ui.chat.state.UiStateViewModel
import ru.zinoview.viewmodelmemoryleak.ui.chat.state.UiStates
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.SnackBar
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.SnackBarHeight
import ru.zinoview.viewmodelmemoryleak.ui.chat.view.ViewWrapper

import ru.zinoview.viewmodelmemoryleak.ui.core.ToolbarActivity
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.Navigation
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.NetworkConnectionFragment


class ChatFragment : NetworkConnectionFragment<ChatViewModel.Base, ChatFragmentBinding>(
    ChatViewModel.Base::class
) {

    private var adapter: ChatAdapter = ChatAdapter.Empty
    private var scrollListener: ChatRecyclerViewScrollListener = ChatRecyclerViewScrollListener.Empty

    private var messageSession: MessageSession = MessageSession.Empty

    private val uiStateViewModel by lazy {
        getKoin().get<UiStateViewModel.Base>()
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
            snackBar
        )

        val diffUtil = ChatMessageDiffUtil()
        adapter = ChatAdapter(diffUtil, object : EditMessageListener {
            override fun edit(message: UiChatMessage) {
                message.show(
                    ViewWrapper.Text(
                        binding.oldMessageTv
                    )
                )

                messageSession.addOldMessage(
                    message.mapToOldMessage()
                )

                messageSession.show(Unit)
                val editMessage = message.map(UiToEditChatMessageMapper())
                messageSession.addMessage(editMessage)
            }
        })

        val manager = LinearLayoutManager(requireContext())
        binding.chatRv.layoutManager = manager
        binding.chatRv.adapter = adapter

        scrollListener = ChatRecyclerViewScrollListener.Base(manager, viewModel)

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
        viewModel.observe(this) { messages ->
            messages.last().changeTitle(requireActivity() as ToolbarActivity)
            adapter.submitList(messages)
        }

        viewModel.observeScrollCommunication(this) { uiScroll ->
            uiScroll.addScrollListener(binding.chatRv,scrollListener)
        }

        viewModel.observeConnection(this) { connection ->
            connection.changeTitle(requireActivity() as ToolbarActivity)
            connection.showError(adapter)
            connection.messages(viewModel)
        }

        uiStateViewModel.observe(this) { states ->
            states.forEach { state ->
                state.recover(binding.messageField,messageSession,
                    ViewWrapper.Text(
                        binding.oldMessageTv
                    )
                )
            }
        }

    }

    override fun onPause() {
        super.onPause()
        val messageSessionState = messageSession.messageSessionState()
        uiStateViewModel.save(
            UiStates.Base(
                UiState.EditText(binding.messageField.text.toString()),
                messageSessionState
            )
        )
    }

    override fun back(navigation: Navigation) = navigation.exit()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): ChatFragmentBinding
        = ChatFragmentBinding.inflate(layoutInflater,container,false)

    override fun dependenciesScope() = SCOPE_NAME

    private companion object {
        private const val SCOPE_NAME = "cufScope"
    }
}