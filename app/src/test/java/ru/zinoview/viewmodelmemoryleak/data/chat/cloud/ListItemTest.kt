package ru.zinoview.viewmodelmemoryleak.data.chat.cloud

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.zinoview.viewmodelmemoryleak.ui.core.Same

/**
 * Test for [ru.zinoview.viewmodelmemoryleak.data.chat.cloud]
 * */

class ListItemTest {

    private var testItem: TestListItem? = null

    @Before
    fun setUp() {
        testItem = TestListItem()
    }

    @Test
    fun test_success_fetch_item_by_argument() {
        val list = listOf(
            User.Base("Jake","Wharthon"),
            User.Base("Amber","Pho"),
            User.Base("Vitaly","Uga")
        )
        val expected = User.Base("Jake","Wharthon")
        val actual = testItem?.item(list,"Jake")

        assertEquals(expected, actual)
    }

    @Test
    fun test_failure_fetch_item_by_argument() {
        val list = listOf(
            User.Base("Jake","Wharthon"),
            User.Base("Amber","Pho"),
            User.Base("Vitaly","Uga")
        )
        val expected = User.Empty
        val actual = testItem?.item(list,"Lifo")

        assertEquals(expected, actual)
    }

    @Test
    fun test_success_fetch_index_item_by_argument() {
        val list = listOf(
            User.Base("Jake","Wharthon"),
            User.Base("Amber","Pho"),
            User.Base("Vitaly","Uga")
        )
        val expected = 1
        val actual = testItem?.index(list,"Amber")

        assertEquals(expected, actual)
    }

    @Test
    fun test_failure_index_item_by_argument() {
        val list = listOf(
            User.Base("Jake","Wharthon"),
            User.Base("Amber","Pho"),
            User.Base("Vitaly","Uga")
        )
        val expected = -1
        val actual = testItem?.index(list,"AmberHow")

        assertEquals(expected, actual)
    }

    @After
    fun clean() {
        testItem = null
    }

    private interface User : Same<String,Unit> {

        data class Base(
            private val name: String,
            private val surname: String
        ) : User {
            override fun same(data: String,arg2: Unit) = name == data
        }

        object Empty : User {
            override fun same(data: String,arg2: Unit) = false
        }
    }

    private inner class TestListItem : ListItem<User>{

        override fun item(src: List<User>, name: String)
            = src.find { user -> user.same(name,Unit) } ?: User.Empty

        override fun index(src: List<User>, name: String): Int {
            var index = -1
            src.forEachIndexed { i,  user ->
                if (user.same(name,Unit)) {
                    index = i
                }
            }
            return index
        }

    }

 }