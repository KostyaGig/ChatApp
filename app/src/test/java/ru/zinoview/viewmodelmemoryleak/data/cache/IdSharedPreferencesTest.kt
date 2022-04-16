package ru.zinoview.viewmodelmemoryleak.data.cache

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Test for [ru.zinoview.viewmodelmemoryleak.data.cache.IdSharedPreferences.Test]
 */

class IdSharedPreferencesTest {

    private var idSharedPreferences: IdSharedPreferences<String,String>? = null
    @Before
    fun setUp() {
        idSharedPreferences = IdSharedPreferences.Test()
    }

    @Test
    fun test_id_is_empty() {
        val expected = true
        val actual = idSharedPreferences?.isEmpty(Unit)

        assertEquals(expected, actual)
    }

    @Test
    fun test_id_is_not_empty() {
        val expected = false

        idSharedPreferences?.save("1")
        val actual = idSharedPreferences?.isEmpty(Unit)

        assertEquals(expected, actual)
    }

    @Test
    fun test_failure_read_empty_id() {
        val expected = ""

        val actual = idSharedPreferences?.read("value_key")
        assertEquals(expected, actual)
    }

    @Test
    fun test_failure_read_id_by_not_existed_key() {
        val expected = ""
        val actual = idSharedPreferences?.read("random_key")
        assertEquals(expected, actual)
    }

    @Test
    fun test_success_read_id_by_key() {
        val expected = "234"
        idSharedPreferences?.save("234")
        val actual = idSharedPreferences?.read("value_key")
        assertEquals(expected, actual)
    }

    @After
    fun clean() {
        idSharedPreferences = null
    }
}