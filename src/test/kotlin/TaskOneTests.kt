import itmo.tg.arcCosineDecomposition
import itmo.tg.factorial
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.math.*

class TaskOneTests {

    @Test
    fun `Factorial of a negative number is undefined`() {
        assertThrows<IllegalArgumentException> { factorial(-1) }
    }

    @Test
    fun `Factorial of 0 is 1`() {
        assertEquals(1, factorial(0))
    }

    @ParameterizedTest
    @MethodSource("factorials")
    fun `Factorial should be calculated correctly`(input: Int, expected: Int) {
        assertEquals(expected, factorial(input))
    }

    @Test
    fun `Arc cosine is undefined outside of (-1, 1)`() {
        assertThrows<IllegalArgumentException> { arcCosineDecomposition(-1.4, 8) }
        assertThrows<IllegalArgumentException> { arcCosineDecomposition(1.3, 8) }
    }

    @Test
    fun `Arc cosine decomposition requires at least 1 step`() {
        assertThrows<IllegalArgumentException> { arcCosineDecomposition(0.0, -12) }
        assertThrows<IllegalArgumentException> { arcCosineDecomposition(0.0, 0) }
    }

    @Test
    fun `Decomposition of acos(0) should be equal to half pi`() {
        assertEquals(PI / 2, arcCosineDecomposition(0.0, 16))
    }

    private val inaccuracy = 10.0.pow(-4)

    @ParameterizedTest
    @MethodSource("arcCosineArguments")
    fun `Arc cosine inaccuracy should be less than 10 to the -4th`(
        input: Double, expected: Double
    ) {
        assert(abs(arcCosineDecomposition(input, 8) - expected) < inaccuracy)
    }

    companion object {
        @JvmStatic fun factorials() = listOf(
            Arguments.of(1, 1),
            Arguments.of(2, 2),
            Arguments.of(3, 6),
            Arguments.of(4, 24),
            Arguments.of(5, 120),
            Arguments.of(6, 720)
        )

        @JvmStatic fun arcCosineArguments() = listOf(
            Arguments.of(0.1, acos(0.1)),
            Arguments.of(-0.1, acos(-0.1)),
            Arguments.of(-0.5, 2 * PI / 3),
            Arguments.of(0.5, PI / 3),
            Arguments.of((sqrt(3.0) - 1) / (2 * sqrt(2.0)), 5 * PI / 12),
            Arguments.of(-(sqrt(3.0) - 1) / (2 * sqrt(2.0)), 7 * PI / 12),
            Arguments.of(0.6, acos(0.6)),
            Arguments.of(-0.6, acos(-0.6))
        )
    }

}
