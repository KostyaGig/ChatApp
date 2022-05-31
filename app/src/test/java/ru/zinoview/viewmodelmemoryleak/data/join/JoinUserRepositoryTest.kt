package ru.zinoview.viewmodelmemoryleak.data.join

import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


/**
 * Test for [ru.zinoview.viewmodelmemoryleak.data.join.JoinUserRepository]
 * */
class JoinUserRepositoryTest {

    private var repository: TestJoinUserRepository? = null

    @Before
    fun setUp() {
        repository = TestJoinUserRepository(
            TestCloudDataSource()
        )
    }

    @Test
    fun test_success_join_user()  = runBlocking {
        var expected = DataJoin.Test("0")
        var actual = repository?.joinedUserId("Kostya")

        assertEquals(expected, actual)

        repository?.joinedUserId("")

        expected = DataJoin.Test("1")
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

    class TestJoinUserRepository(
        private val cloudDataSource: TestCloudDataSource
    ) {

        suspend fun joinedUserId(nickname: String): DataJoin {
            val userId = cloudDataSource.joinedUserId(nickname)

            return if (userId == "-1") {
                DataJoin.Failure("User nickname must not be empty")
            } else {
                DataJoin.Test(userId)
            }
        }

    }

    inner class TestCloudDataSource {

        private var id = -1

        fun joinedUserId(nickname: String) : String {
            return if (nickname.isEmpty()) {
                "-1"
            } else {
                id += 1
                "$id"
            }
        }
    }

}