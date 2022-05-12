package ru.zinoview.viewmodelmemoryleak.data.chat

import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudMessage
import ru.zinoview.viewmodelmemoryleak.data.chat.cloud.CloudToDataMessageMapper

/**
 * Test for [ru.zinoview.viewmodelmemoryleak.data.chat.ChatRepository]
 */

class ChatRepositoryTest {

    private var repository: TestChatRepository? = null

    @Before
    fun setUp() {
        repository = TestChatRepository(
            TestCloudDataSource(),
            TestCloudToDataMessageMapper("1")
        )
    }

    @Test
    fun test_receive_empty_messages() = runBlocking {
        val expected = listOf(DataMessage.Failure("Messages are empty"))
        val actual = repository?.messages()

        assertEquals(expected, actual)
    }

    @Test
    fun test_success_send_messages() = runBlocking {

        repository?.sendMessage("Hi,Bob")
        repository?.sendMessage("Hello!")
        repository?.sendMessage("How are you?")
        repository?.sendMessage("I'm fine")

        val expected = listOf(
            DataMessage.Sent.Unread("-1","1","Hi,Bob","-1"),
            DataMessage.Received("-1","2","Hello!","-1"),
            DataMessage.Sent.Unread("-1","1","How are you?","-1"),
            DataMessage.Received("-1","2","I'm fine","-1"),
        )
        repository?.messages()
        val actual = repository?.messages()

        assertEquals(expected, actual)
    }

    @Test
    fun test_failure_send_messages() = runBlocking {

        repository?.sendMessage("Hi,Bob")

        val expected = listOf(
            DataMessage.Failure("Messages are empty")
        )
        val actual = repository?.messages()

        assertEquals(expected, actual)
    }

    @Test
    fun test_update_content_message_by_id() = runBlocking {

        repository?.sendMessage("Hi,Bob")
        repository?.sendMessage("Hello!")
        repository?.sendMessage("How are you?")
        repository?.sendMessage("I'm fine")

        val expected = listOf(
            DataMessage.Sent.Unread("-1","1","Hi,Bob","-1"),
            DataMessage.Received("-1","2","Hello!","-1"),
            DataMessage.Sent.Unread("-1","1","What are you doing?","-1"),
            DataMessage.Received("-1","2","I'm fine","-1"),
        )

        repository?.editMessage("3","What are you doing?")
        repository?.messages()
        val actual = repository?.messages()
        assertEquals(expected, actual)
    }

    @Test
    fun test_read_messages() = runBlocking {
        repository?.sendMessage("Hi,Bob")
        repository?.sendMessage("Hello!")
        repository?.sendMessage("How are you?")
        repository?.sendMessage("I'm fine")

        var expected = listOf(
            DataMessage.Sent.Unread("-1","1","Hi,Bob","-1"),
            DataMessage.Received("-1","2","Hello!","-1"),
            DataMessage.Sent.Unread("-1","1","What are you doing?","-1"),
            DataMessage.Received("-1","2","I'm fine","-1"),
        )

        repository?.editMessage("3","What are you doing?")
        repository?.messages()
        var actual = repository?.messages()
        assertEquals(expected, actual)

        expected = listOf(
            DataMessage.Sent.Read("-1","1","Hi,Bob","-1"),
            DataMessage.Received("-1","2","Hello!","-1"),
            DataMessage.Sent.Read("-1","1","What are you doing?","-1"),
            DataMessage.Received("-1","2","I'm fine","-1"),
        )

        repository?.readMessages(Pair(0,3))
        repository?.messages()
        actual = repository?.messages()
        assertEquals(expected, actual)
    }

    @Test
    fun test_to_type_message_is_typing() = runBlocking {
        val expected = DataMessage.Typing.Is("Bob")
        val actual = repository?.toTypeMessage(true)
        assertEquals(expected, actual)
    }

    @Test
    fun test_to_type_message_is_not_typing() = runBlocking {
        val expected = DataMessage.Typing.IsNot("Bob")
        val actual = repository?.toTypeMessage(false)
        assertEquals(expected, actual)
    }

    @Test
    fun test_to_type_mix_states() = runBlocking {
        var expected: DataMessage = DataMessage.Typing.IsNot("Bob")
        var actual = repository?.toTypeMessage(false)
        assertEquals(expected, actual)

        expected = DataMessage.Typing.Is("Bob")
        actual = repository?.toTypeMessage(true)
        assertEquals(expected, actual)

        expected = DataMessage.Typing.Is("Bob")
        actual = repository?.toTypeMessage(true)
        assertEquals(expected, actual)
    }

    @After
    fun clean() {
        repository = null
    }

    class TestChatRepository(
        private val cloudDataSource: TestCloudDataSource,
        private val mapper: CloudToDataMessageMapper
    ) : ChatRepository<DataMessage> {
        override fun clean() = Unit

        private var count = 0

        override suspend fun editMessage(messageId: String, content: String)
                = cloudDataSource.editMessage(messageId, content)

        override suspend fun sendMessage(content: String) {
            val userId = if (count % 2 == 0 ) 1 else 2
            cloudDataSource.sendMessage(userId.toString(),"fake nick name",content)
            count++
        }

        override fun readMessages(range: Pair<Int, Int>)
            = cloudDataSource.readMessages(range)

        override suspend fun toTypeMessage(isTyping: Boolean)
            = cloudDataSource.toTypeMessage(isTyping,"Bob").first().map(mapper)

        override suspend fun showNotificationMessage(messageId: String) = Unit

        fun messages() : List<DataMessage> = cloudDataSource.messages().map { it.map(mapper) }
    }

    class TestCloudDataSource : CloudDataSource<List<CloudMessage>> {

        private val messages = mutableListOf<CloudMessage.Test>()
        private var isSuccess = false

        override suspend fun sendMessage(userId: String, nickName: String ,content: String) {
            messages.add(
                CloudMessage.Test(
                "-1",userId,content,false,"-1"
            ))
        }

        fun messages() : List<CloudMessage>{
            val result = if (isSuccess) {
                messages
            } else {
                listOf(CloudMessage.Failure("Messages are empty"))
            }
            isSuccess = !isSuccess
            return result
        }

        override fun readMessages(range: Pair<Int, Int>) {
            for (index in range.first..range.second) {
                val message = messages[index]
                messages[index] = message.read()
            }
        }

        override fun disconnect(arg: Unit) = messages.clear()

        override suspend fun editMessage(messageId: String, content: String) {
            val message = messages[messageId.toInt() - 1]
            messages[messageId.toInt() - 1]  = message.updated(content)
        }

        override suspend fun toTypeMessage(
            isTyping: Boolean,
            senderNickName: String
        ) = listOf(CloudMessage.Typing(senderNickName,isTyping))
    }

    private inner class TestCloudToDataMessageMapper(
        private val senderId: String
    ) : CloudToDataMessageMapper,Mapper.Base<DataMessage>(
        DataMessage.Empty
    ) {
        override fun map(
            id: String,
            senderId: String,
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

        override fun mapIsTyping(senderNickname: String)
            = DataMessage.Typing.Is(senderNickname)

        override fun mapIsNotTyping(senderNickname: String)
            = DataMessage.Typing.IsNot(senderNickname)
    }


}