package ru.zinoview.viewmodelmemoryleak.data.authentication

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Test for [ru.zinoview.viewmodelmemoryleak.data.authentication.AuthenticationRepository.Test]
 */

class AuthenticationRepositoryTest {

    private var repository: AuthenticationRepository? = null

    @Before
    fun setUp() {
        repository = AuthenticationRepository.Test()
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
}