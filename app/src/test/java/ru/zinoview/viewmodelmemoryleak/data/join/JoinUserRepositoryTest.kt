package ru.zinoview.viewmodelmemoryleak.data.join

import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.zinoview.viewmodelmemoryleak.data.join.cloud.CloudDataSource


/**
 * Test for [ru.zinoview.viewmodelmemoryleak.data.join.JoinUserRepository.Test]
 * */
class JoinUserRepositoryTest {

    private var repository: JoinUserRepository? = null

    @Before
    fun setUp() {
        repository = JoinUserRepository.Test(
            CloudDataSource.Test()
        )
    }

    @Test
    fun test_success_join_user()  = runBlocking {
        var expected = DataJoin.Test(0)
        var actual = repository?.joinedUserId("Kostya")

        assertEquals(expected, actual)

        repository?.joinedUserId("")

        expected = DataJoin.Test(1)
        actual = repository?.joinedUserId("Nusha")

        assertEquals(expected, actual)
    }

    @Test
    fun test_failure_join_user_empty_nickname() = runBlocking {
        val expected = DataJoin.Failure("User nickname must not be empty")
        val actual = repository?.joinedUserId("")
        assertEquals(expected, actual)
    }


    @After
    fun clean() {
        repository = null
    }
}