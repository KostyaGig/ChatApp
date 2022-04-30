package ru.zinoview.viewmodelmemoryleak.data.authentication

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Test for [ru.zinoview.viewmodelmemoryleak.data.authentication.AuthenticationRepository]
 */

class AuthenticationRepositoryTest {

    private var repository: TestAuthenticationRepository? = null

    @Before
    fun setUp() {
        repository = TestAuthenticationRepository()
    }

    @Test
    fun test_failure_auth() {
        val expected = DataAuth.Failure
        val actual = repository?.auth()

        assertEquals(expected, actual)
    }

    @Test
    fun test_success_auth() {
        val expected = DataAuth.Success
        repository?.auth()
        val actual = repository?.auth()
        assertEquals(expected, actual)
    }

    @After
    fun clean() {
        repository = null
    }

    class TestAuthenticationRepository : AuthenticationRepository {

        private var isAuth = false

        override fun auth(): DataAuth {
            val result = if (isAuth) {
                DataAuth.Success
            } else {
                DataAuth.Failure
            }
            isAuth = isAuth.not()
            return result
        }

        override fun clean() = Unit
    }
}