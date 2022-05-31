package ru.zinoview.viewmodelmemoryleak.data.chat.ui_state

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.zinoview.viewmodelmemoryleak.data.chat.DataMessage
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.ChatUiState
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.ChatUiStates

/**
 * Test for [ru.zinoview.viewmodelmemoryleak.data.chat.ui_state.UiStateRepository]
 * */

class UiStateRepositoryTest {

    private var repository: TestUiStateRepository? = null

    @Before
    fun setUp() {
        repository = TestUiStateRepository()
    }

    @Test
    fun test_save_empty_state() {
        val expected = ChatUiStates.Empty
        var actual = repository?.read(Unit){it.map()}

        assertEquals(expected, actual)

        repository?.save(ChatUiStates.Base.Test(
            ChatUiState.EditText(),
            ChatUiState.MessageSession()
        ))

        actual = repository?.read(Unit){it.map()}

        repository?.save(ChatUiStates.Base.Test(
            ChatUiState.EditText(),
            ChatUiState.MessageSession()
        ))
        assertEquals(expected, actual)
    }

    @Test
    fun test_save_full_state() {
        var expected = ChatUiStates.Base.Test(
            ChatUiState.EditText("Restored text"),
            ChatUiState.MessageSession("edited message's text","12345")
        )

        repository?.save(expected)

        var actual = repository?.read(Unit){it.map()}
        assertEquals(expected, actual)

        expected = ChatUiStates.Base.Test(
            ChatUiState.EditText("New text"),
            ChatUiState.MessageSession("another text","12345")
        )

        repository?.save(expected)

        actual = repository?.read(Unit) {it.map()}
        assertEquals(expected, actual)
    }

    @Test
    fun test_save_state_partly() {
        var expected = ChatUiStates.Base.Test(
            ChatUiState.EditText(),
            ChatUiState.MessageSession("edited message's text","12345")
        )

        repository?.save(expected)

        var actual = repository?.read(Unit){it.map()}
        assertEquals(expected, actual)

        expected = ChatUiStates.Base.Test(
            ChatUiState.EditText("New text"),
            ChatUiState.MessageSession()
        )

        repository?.save(expected)

        actual = repository?.read(Unit){it.map()}
        assertEquals(expected, actual)
    }

    @After
    fun clean() {
        repository = null
    }

    class TestUiStateRepository : ChatUiStateRepository {

        private var uiState: ChatUiStates = ChatUiStates.Empty

        override fun read(key: Unit, map: (ChatUiStates.Base) -> ChatUiStates): ChatUiStates {
            return if ((uiState is ChatUiStates.Empty).not()) {
                (uiState as ChatUiStates.Base.Test).map()
            } else {
                ChatUiStates.Empty
            }
        }

        override fun save(state: ChatUiStates.Base) {
            uiState = state
        }

        override fun messages(): List<DataMessage> = emptyList()
    }
}