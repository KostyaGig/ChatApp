package ru.zinoview.viewmodelmemoryleak.data.join

import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.zinoview.viewmodelmemoryleak.data.join.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.ui.join.ImageProfile


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
        var actual = repository?.joinedUserId(ImageProfile.Default,"Kostya")

        assertEquals(expected, actual)

        repository?.joinedUserId(ImageProfile.Default,"")

        expected = DataJoin.Test("1")
        actual = repository?.joinedUserId(ImageProfile.Default,"Nusha")

        assertEquals(expected, actual)
    }

    @Test
    fun test_failure_join_user_empty_nickname() = runBlocking {
        val expected = DataJoin.Failure("User nickname must not be empty")
        val actual = repository?.joinedUserId(ImageProfile.Default,"")
        assertEquals(expected, actual)
    }


    @After
    fun clean() {
        repository = null
    }

    class TestJoinUserRepository(
        private val cloudDataSource: CloudDataSource
    ) : JoinUserRepository {

        override suspend fun joinedUserId(image: ImageProfile,nickname: String): DataJoin {
            val userId = cloudDataSource.joinedUserId(image,nickname)

            return if (userId == "-1") {
                DataJoin.Failure("User nickname must not be empty")
            } else {
                DataJoin.Test(userId)
            }
        }

        override fun clean()  = Unit
    }

    class TestCloudDataSource : CloudDataSource {

        private var id = -1

        override suspend fun joinedUserId(image: ImageProfile,nickname: String): String {
            return if (nickname.isEmpty()) {
                "-1"
            } else {
                id += 1
                "$id"
            }
        }

        override fun disconnect(arg: Unit) = Unit
    }
}