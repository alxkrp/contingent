import kotlin.test.Test
import kotlin.test.assertEquals

class StubTest {

    var x = 3

    @Test
    fun test1() {
        assertEquals(x, 2+2/2)
    }

    @Test
    fun test2() {
        assertEquals(x, 2+2/2)
    }
}