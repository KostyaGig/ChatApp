package ru.zinoview.viewmodelmemoryleak.data.chat.ui_state

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.zinoview.viewmodelmemoryleak.data.chat.DataMessage
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.UiState
import ru.zinoview.viewmodelmemoryleak.ui.chat.ui_state.UiStates

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
        val expected = UiStates.Test.Empty
        var actual = repository?.read(Unit)

        assertEquals(expected, actual)

        repository?.save(UiStates.Test.Base(
            UiState.EditText(),
            UiState.MessageSession()
        ))

        actual = repository?.read(Unit)

        repository?.save(UiStates.Test.Base(
            UiState.EditText(),
            UiState.MessageSession()
        ))
        assertEquals(expected, actual)
    }

    @Test
    fun test_save_full_state() {
        var expected = UiStates.Test.Base(
            UiState.EditText("Restored text"),
            UiState.MessageSession("edited message's text","12345")
        )

        repository?.save(expected)

        var actual = repository?.read(Unit)
        assertEquals(expected, actual)

        expected = UiStates.Test.Base(
            UiState.EditText("New text"),
            UiState.MessageSession("another text","12345")
        )

        repository?.save(expected)

        actual = repository?.read(Unit)
        assertEquals(expected, actual)
    }

    @Test
    fun test_save_state_partly() {
        var expected = UiStates.Test.Base(
            UiState.EditText(),
            UiState.MessageSession("edited message's text","12345")
        )

        repository?.save(expected)

        var actual = repository?.read(Unit)
        assertEquals(expected, actual)

        expected = UiStates.Test.Base(
            UiState.EditText("New text"),
            UiState.MessageSession()
        )

        repository?.save(expected)

        actual = repository?.read(Unit)
        assertEquals(expected, actual)
    }

    @After
    fun clean() {
        repository = null
    }

    class TestUiStateRepository : UiStateRepository {

        private var uiState: UiStates = UiStates.Test.Empty

        override fun save(state: UiStates) {
            uiState = state
        }

        override fun read(key: Unit) = (uiState as UiStates.Test).map()

        override fun messages(): List<DataMessage> = emptyList()
    }
}