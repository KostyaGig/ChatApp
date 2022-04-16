package ru.zinoview.viewmodelmemoryleak.data.connection

import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.zinoview.viewmodelmemoryleak.data.connection.cloud.CloudDataSource


/**
 * Test for [ru.zinoview.viewmodelmemoryleak.data.connection.ConnectionRepository.Test]
 */

class ConnectionRepositoryTest {

    private var repository: ConnectionRepository<DataConnection>? = null

    @Before
    fun setUp() {
        repository = ConnectionRepository.Test(
            CloudDataSource.Test(),
            CloudToDataConnectionMapper(),
        )
    }

    @Test
    fun test_success_connection() = runBlocking {
        val expected = DataConnection.Success
        repository?.updateNetworkConnection(true)
        val actual = repository?.updateNetworkConnection(true)
        assertEquals(expected,actual)
    }

    @Test
    fun test_failure_connection_waiting_for_network() = runBlocking {
        val expected = DataConnection.Failure("Waiting for network...")
        val actual = repository?.updateNetworkConnection(false)
        assertEquals(expected,actual)
    }

    @Test
    fun test_failure_connection_waiting_for_server() = runBlocking {
        val expected = DataConnection.Failure("Waiting for server...")
        val actual = repository?.updateNetworkConnection(true)
        assertEquals(expected,actual)
    }

    @After
    fun clean() {
        repository = null
    }
 }