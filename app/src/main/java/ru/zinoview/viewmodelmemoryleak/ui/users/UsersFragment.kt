package ru.zinoview.viewmodelmemoryleak.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.android.ext.android.get
import org.koin.android.ext.android.getKoin
import ru.zinoview.viewmodelmemoryleak.databinding.UsersFragmentBinding
import ru.zinoview.viewmodelmemoryleak.ui.chat.NetworkConnectionReceiver
import ru.zinoview.viewmodelmemoryleak.ui.connection.ConnectionViewModel
import ru.zinoview.viewmodelmemoryleak.ui.core.Adapter
import ru.zinoview.viewmodelmemoryleak.ui.core.ToolbarActivity
import ru.zinoview.viewmodelmemoryleak.ui.core.navigation.NetworkConnectionFragment

class UsersFragment : NetworkConnectionFragment<UsersViewModel.Base,UsersFragmentBinding>(
    UsersViewModel.Base::class
) {

    private var adapter: Adapter<List<UiUser>> = Adapter.Empty()

    private val connectionViewModel by lazy {
        getKoin().getOrCreateScope(CONNECTION_SCOPE)
        get<ConnectionViewModel.Base>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        networkConnectionReceiver = NetworkConnectionReceiver.Base(connectionViewModel)

        val diffUtil = UsersDiffUtil()
        adapter = UsersAdapter(diffUtil)

        binding.usersRecView.adapter = adapter as UsersAdapter

        connectionViewModel.connection()
    }

    override fun onStart() {
        super.onStart()

        viewModel.observe(this) { users ->
            users.update(adapter)
            users.changeTitle(requireActivity() as ToolbarActivity)
        }

        connectionViewModel.observe(this) { connection ->
            connection.changeTitle(requireActivity() as ToolbarActivity)
            connection.doAction { viewModel.users() }
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = UsersFragmentBinding.inflate(inflater,container,false)

    override fun dependenciesScope() = SCOPE
    private companion object {
        private const val SCOPE = "usfScope"
        private const val CONNECTION_SCOPE = "cvfScope"
    }
}