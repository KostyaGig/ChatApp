package ru.zinoview.viewmodelmemoryleak.data.connection

import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.zinoview.viewmodelmemoryleak.data.connection.cloud.CloudConnection
import ru.zinoview.viewmodelmemoryleak.data.connection.cloud.CloudDataSource


/**
 * Test for [ru.zinoview.viewmodelmemoryleak.data.connection.ConnectionRepository]
 */

class ConnectionRepositoryTest {

    private var repository: TestConnectionRepository? = null

    @Before
    fun setUp() {
        repository = TestConnectionRepository(
            TestCloudDataSource(),
            CloudToDataConnectionMapper(),
        )
    }

    @Test
    fun test_success_connection() = runBlocking {
        val expected = DataConnection.Success
        repository?.connection(true)
        val actual = repository?.connection(true)
        assertEquals(expected,actual)
    }

    @Test
    fun test_failure_connection_waiting_for_network() = runBlocking {
        val expected = DataConnection.Failure("Waiting for network...")
        val actual = repository?.connection(false)
        assertEquals(expected,actual)
    }

    @Test
    fun test_failure_connection_waiting_for_server() = runBlocking {
        val expected = DataConnection.Failure("Waiting for server...")
        val actual = repository?.connection(true)
        assertEquals(expected,actual)
    }

    @After
    fun clean() {
        repository = null
    }

    class TestConnectionRepository(
        private val cloudDataSource: CloudDataSource<CloudConnection>,
        private val mapper: CloudToDataConnectionMapper
    ) : ConnectionRepository<DataConnection> {

        override suspend fun observe(block: (DataConnection) -> Unit) {

        }

        override suspend fun connection(isConnected: Boolean): DataConnection {
            val cloud = cloudDataSource.connection(isConnected)
            return cloud.map(mapper)
        }
    }

    class TestCloudDataSource : CloudDataSource<CloudConnection> {

        private var count = 0

        override fun disconnect(arg: Unit) {
            count = 0
        }

        override suspend fun observe(block: (CloudConnection) -> Unit) = Unit

        override suspend fun connection(isConnected: Boolean) : CloudConnection {
            return if (isConnected) {
                val result = if (count % 2 == 0) {
                    CloudConnection.Message("Waiting for server...")
                } else {
                    CloudConnection.Success
                }
                count++
                result
            } else {
                CloudConnection.Message("Waiting for network...")
            }
        }

    }
 }