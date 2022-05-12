package ru.zinoview.viewmodelmemoryleak.data.chat

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.zinoview.viewmodelmemoryleak.core.chat.Mapper

/**
 * Test for [ru.zinoview.viewmodelmemoryleak.core.chat.Mapper]
 * */


class CloudToDataMessageMapperTest {
    private var mapper: DataToUiTestMessageMapper? = null


    @Before
    fun setUp() {
        mapper = DataToUiTestMessageMapper()
    }

    @Test
    fun test_map_received_data_to_ui_received_message() {
        val data =  DataTestMessage.Received(
            "1","123","Hi, John!","Bob"
        )
        val expected = UiTestMessage.Received(
            "1","123","Hi, John!","Bob"
        )
        val actual = data.map(mapper!!)
        assertEquals(expected, actual)
    }

    @Test
    fun test_map_received_data_to_ui_sent_message() {
        val data =  DataTestMessage.Sent(
            "2","321","How are you?","Bob"
        )
        val expected = UiTestMessage.Sent(
            "2","321","How are you?","Bob"
        )
        val actual = data.map(mapper!!)
        assertEquals(expected, actual)
    }

    @Test
    fun test_map_failure() {
        val data = DataTestMessage.Failure(
            "Failure message's text"
        )

        val expected = UiTestMessage.Failure(
            "Failure message's text"
        )
        val actual = data.map(mapper!!)
        assertEquals(expected, actual)
    }

    @After
    fun clean() {
        mapper = null
    }



    private inner class DataToUiTestMessageMapper : Mapper.Base<UiTestMessage>(
        UiTestMessage.Empty
    ) {

        override fun mapFailure(message: String): UiTestMessage
            = UiTestMessage.Failure(message)

        override fun map(
            id: String,
            senderId: String,
            content: String,
            senderNickname: String
        ): UiTestMessage = UiTestMessage.Sent(id, senderId, content, senderNickname)

        override fun mapReceived(
            id: String,
            senderId: String,
            content: String,
            senderNickname: String
        ): UiTestMessage = UiTestMessage.Received(id, senderId, content, senderNickname)

    }

    interface DataTestMessage {

        fun <T> map(mapper: Mapper<T>) : T

        class Sent(
            private val id: String,
            private val senderId: String,
            private val content: String,
            private val senderNickname: String
        ) : DataTestMessage {
            override fun <T> map(mapper: Mapper<T>): T
                = mapper.map(id, senderId, content, senderNickname)
        }

        class Received(
            private val id: String,
            private val senderId: String,
            private val content: String,
            private val senderNickname: String
        ) : DataTestMessage {
            override fun <T> map(mapper: Mapper<T>): T
                = mapper.mapReceived(id, senderId, content, senderNickname)
        }

        class Failure(
            private val message: String
        ) : DataTestMessage {

            override fun <T> map(mapper: Mapper<T>): T
                = mapper.mapFailure(message)
        }

        object Empty : DataTestMessage {
            override fun <T> map(mapper: Mapper<T>): T = mapper.map()
        }
    }

    private interface UiTestMessage {

        fun <T> map(mapper: Mapper<T>) : T

        data class Sent(
            private val id: String,
            private val senderId: String,
            private val content: String,
            private val senderNickname: String
        ) : UiTestMessage {
            override fun <T> map(mapper: Mapper<T>): T
                = mapper.map(id, senderId, content, senderNickname)
        }

        data class Received(
            private val id: String,
            private val senderId: String,
            private val content: String,
            private val senderNickname: String
        ) : UiTestMessage {
            override fun <T> map(mapper: Mapper<T>): T
                    = mapper.mapReceived(id, senderId, content, senderNickname)
        }

        data class Failure(
            private val message: String
        ) : UiTestMessage {

            override fun <T> map(mapper: Mapper<T>): T
                    = mapper.mapFailure(message)
        }

        object Empty : UiTestMessage {
            override fun <T> map(mapper: Mapper<T>): T = mapper.map()
        }
    }

    class TestCloudToDataMessageMapper : Mapper.Base<DataTestMessage>(DataTestMessage.Empty) {

        override fun map(
            id: String,
            senderId: String,
            content: String,
            senderNickname: String
        ): DataTestMessage {
            return if (senderId == "1") {
                DataTestMessage.Sent(id, senderId, content, senderNickname)
            } else {
                DataTestMessage.Received(id, senderId, content, senderNickname)
            }
        }

        override fun mapFailure(message: String): DataTestMessage
            = DataTestMessage.Failure(message)
    }
}