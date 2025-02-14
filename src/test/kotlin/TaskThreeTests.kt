import itmo.tg.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class TaskThreeTests {

    @Test
    fun `When she has nothing to say, she says so`() {
        val she = Woman()
        assertEquals("I have nothing to say.", she.bringMessage(null))
        assertEquals("I have nothing to say.", she.bringMessage(""))
        assertEquals("I have nothing to say.", she.bringMessage("    "))
    }

    @Test
    fun `When she has something to say, she says it`() {
        val she = Woman()
        assertEquals("I bring a very important message: Amogus!",
            she.bringMessage("Amogus!"))
    }

    @Test
    fun `She is smarter than men`() {
        val she = Woman()
        val someMan = Man()
        assertEquals(Intelligence.SMART, she.observe(she))
        assertEquals(Intelligence.NOT_SO_SMART, she.observe(someMan))
    }

    @Test
    fun `Most observers think that men are the second smartest beings`() {
        val observers = getObservers()
        val she = Woman()
        val herScore = observers.stream().filter {
            o -> o.observe(she) == Intelligence.SMART }.count()
        val someMan = Man()
        val someMansScore = observers.stream().filter {
            o -> o.observe(someMan) == Intelligence.SMART }.count()
        assert(herScore < someMansScore)
    }

    @Test
    fun `Observers only observe men, women and gods`() {
        val man = Man()
        assertThrows<IllegalArgumentException> { man.observe(Monkey()) }
    }

    @ParameterizedTest
    @MethodSource("testMessages")
    fun `They could have heard her`(message: String) {
        val she = Woman()
        val observers = getObservers()
        observers.forEach { o -> o.hear(she.bringMessage(message)) }
        assert(observers.all { o -> o.speak().equals(
            "I bring a very important message: $message") })
    }

    companion object {
        @JvmStatic
        fun testMessages() = listOf(
            Arguments.of("Amogus!"),
            Arguments.of("I am smart!"),
            Arguments.of("Make America Great Again!")
        )
    }

}