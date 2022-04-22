package ru.zinoview.viewmodelmemoryleak.data.chat

import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudToDataMessageMapper

/**
 * Test for [ru.zinoview.viewmodelmemoryleak.data.chat.ChatRepository.Test]
 */

class ChatRepositoryTest {

    private var repository: ChatRepository<List<DataMessage>>? = null

    @Before
    fun setUp() {
        repository = ChatRepository.Test(
            CloudDataSource.Test(),
            TestCloudToDataMessageMapper(
                1
            )
        )
    }

    @Test
    fun test_receive_empty_messages() = runBlocking {
        val expected = listOf(DataMessage.Failure("Messages are empty"))
        val actual = repository?.messages {}

        assertEquals(expected, actual)
    }

    @Test
    fun test_success_send_messages() = runBlocking {

        repository?.sendMessage("Hi,Bob")
        repository?.sendMessage("Hello!")
        repository?.sendMessage("How are you?")
        repository?.sendMessage("I'm fine")

        val expected = listOf(
            DataMessage.Sent.Unread("-1",1,"Hi,Bob","-1"),
            DataMessage.Received("-1",2,"Hello!","-1"),
            DataMessage.Sent.Unread("-1",1,"How are you?","-1"),
            DataMessage.Received("-1",2,"I'm fine","-1"),
        )
        repository?.messages {}
        val actual = repository?.messages {}

        assertEquals(expected, actual)
    }

    @Test
    fun test_failure_send_messages() = runBlocking {

        repository?.sendMessage("Hi,Bob")

        val expected = listOf(
            DataMessage.Failure("Messages are empty")
        )
        val actual = repository?.messages {}

        assertEquals(expected, actual)
    }

    @Test
    fun test_update_content_message_by_id() = runBlocking {

        repository?.sendMessage("Hi,Bob")
        repository?.sendMessage("Hello!")
        repository?.sendMessage("How are you?")
        repository?.sendMessage("I'm fine")

        val expected = listOf(
            DataMessage.Sent.Unread("-1",1,"Hi,Bob","-1"),
            DataMessage.Received("-1",2,"Hello!","-1"),
            DataMessage.Sent.Unread("-1",1,"What are you doing?","-1"),
            DataMessage.Received("-1",2,"I'm fine","-1"),
        )

        repository?.editMessage("3","What are you doing?")
        repository?.messages {}
        val actual = repository?.messages {}
        assertEquals(expected, actual)
    }

    @Test
    fun test_read_messages() = runBlocking {
        repository?.sendMessage("Hi,Bob")
        repository?.sendMessage("Hello!")
        repository?.sendMessage("How are you?")
        repository?.sendMessage("I'm fine")

        var expected = listOf(
            DataMessage.Sent.Unread("-1",1,"Hi,Bob","-1"),
            DataMessage.Received("-1",2,"Hello!","-1"),
            DataMessage.Sent.Unread("-1",1,"What are you doing?","-1"),
            DataMessage.Received("-1",2,"I'm fine","-1"),
        )

        repository?.editMessage("3","What are you doing?")
        repository?.messages {}
        var actual = repository?.messages {}
        assertEquals(expected, actual)

        expected = listOf(
            DataMessage.Sent.Read("-1",1,"Hi,Bob","-1"),
            DataMessage.Received("-1",2,"Hello!","-1"),
            DataMessage.Sent.Read("-1",1,"What are you doing?","-1"),
            DataMessage.Received("-1",2,"I'm fine","-1"),
        )

        repository?.updateMessagesState(Pair(0,3))
        repository?.messages {}
        actual = repository?.messages {}
        assertEquals(expected, actual)
    }

    @After
    fun clean() {
        repository = null
    }

    private inner class TestCloudToDataMessageMapper(
        private val senderId: Int
    ) : CloudToDataMessageMapper,Mapper.Base<DataMessage>(
        DataMessage.Empty
    ) {
        override fun map(
            id: String,
            senderId: Int,
            content: String,
            senderNickname: String,
            isRead: Boolean
        ): DataMessage {
            return if (this.senderId == senderId) {
                return if (isRead) {
                    DataMessage.Sent.Read(id, senderId, content, senderNickname)
                } else {
                    DataMessage.Sent.Unread(id, senderId, content, senderNickname)
                }
            } else {
                DataMessage.Received(id, senderId, content, senderNickname)
            }
        }

        override fun mapFailure(message: String)
            = DataMessage.Failure(message)
    }
}