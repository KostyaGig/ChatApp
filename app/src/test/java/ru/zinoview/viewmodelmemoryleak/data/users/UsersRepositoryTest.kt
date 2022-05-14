package ru.zinoview.viewmodelmemoryleak.data.users

/**
 * Test for [ ru.zinoview.viewmodelmemoryleak.data.users.UsersRepository ]
 * */

import android.net.Uri
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.zinoview.viewmodelmemoryleak.core.users.AbstractUser
import ru.zinoview.viewmodelmemoryleak.core.users.UserMapper
import ru.zinoview.viewmodelmemoryleak.data.users.cloud.CloudDataSource
import ru.zinoview.viewmodelmemoryleak.data.users.cloud.CloudUser
import java.lang.IllegalStateException

class UsersRepositoryTest {

    private var repository: UsersRepository? = null

    @Before
    fun setUp() {
        repository = TestUsersRepository(
            TestMapper(),
            TestCloudDataSource()
        )
    }

    @Test
    fun test_fetch_users_failure_no_connection() = runBlocking {
        val expected = DataUsers.Failure("No connection")
        val actual = repository?.data()

        assertEquals(expected, actual)
    }


    @Test
    fun test_fetch_users_failure_empty() = runBlocking{
        val expected = DataUsers.Failure("Users are empty")
        repository?.data()
        val actual = repository?.data()

        assertEquals(expected, actual)
    }

    @Test
    fun test_fetch_users_success() = runBlocking{
        val expected = DataUsers.TestSuccess(
            listOf(
                AbstractUser.Test(
                    "1","Bob"
                ),
                AbstractUser.Test(
                    "2","Jake"
                ),
                AbstractUser.Test(
                    "3","Lifo"
                )
            ),"Users - 3"
        )
        repository?.data()
        repository?.data()
        val actual = repository?.data()

        assertEquals(expected, actual)
    }

    @After
    fun clean() {
        repository = null
    }
    class TestUsersRepository(
        private val mapper: UserMapper.Test,
        private val cloudDataSource: CloudDataSource
    ) : UsersRepository {

        private var isNotSuccess = true

        override suspend fun data(): DataUsers {
            return if(isNotSuccess) {
                isNotSuccess = false
                DataUsers.Failure("No connection")
            } else {
                val users = cloudDataSource.users("").map { it as CloudUser.Base.Test }.map {
                    it.map(mapper)
                }
                return if (users.isEmpty()) {
                    DataUsers.Failure("Users are empty")
                } else {
                    DataUsers.TestSuccess(users,"Users - ${users.size}")
                }
            }
        }

    }

    class TestCloudDataSource : CloudDataSource {

        private var isEmpty = true

        override suspend fun users(userId: String): List<CloudUser> {
            return if (isEmpty) {
                isEmpty = false
                emptyList()
            } else {
                isEmpty = true
                listOf(
                    CloudUser.Base.Test(
                        "1","Bob","BobImage"
                    ),
                    CloudUser.Base.Test(
                        "2","Jake","JakeImage"
                    ),
                    CloudUser.Base.Test(
                        "3","Lifo","LifoImage"
                    )
                )
            }
        }
    }

    class TestMapper : UserMapper.Test {

        override fun map(userId: String, nickName: String): AbstractUser
            = AbstractUser.Test(userId,nickName)
    }

    class TestBitmap : ru.zinoview.viewmodelmemoryleak.ui.join.Bitmap {
        override fun bitmap(uri: Uri)
            = throw IllegalStateException("Bitmap.bitmap(base64String: String)")

        override fun bitmap(base64String: String)
            = throw IllegalStateException("Bitmap.bitmap(base64String: String)")
    }
}