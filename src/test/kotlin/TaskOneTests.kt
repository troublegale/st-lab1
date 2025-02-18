import itmo.tg.arcCosineDecomposition
import itmo.tg.factorial
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

const val PI = 3.141592653589793

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

    private val inaccuracy = 0.0001

    @ParameterizedTest
    @MethodSource("arcCosineArguments")
    fun `Arc cosine inaccuracy should be less than 10 to the -4th`(
        input: Double, expected: Double
    ) {
        var difference = arcCosineDecomposition(input, 8) - expected
        difference = if (difference > 0) difference else -difference
        assert(difference < inaccuracy)
    }

    companion object {
        @JvmStatic
        fun factorials() = listOf(
            Arguments.of(1, 1),
            Arguments.of(2, 2),
            Arguments.of(3, 6),
            Arguments.of(4, 24),
            Arguments.of(5, 120),
            Arguments.of(6, 720)
        )

        private const val ACOS_0_1  = 1.4706289056333368 // acos(0.1)
        private const val ACOS_N_0_1 = 1.6709637479564565 // acos(-0.1)
        private const val ACOS_0_6 = 0.9272952180016123 // acos(0.6)
        private const val ACOS_N_0_6 = 2.214297435588181 // acos(-0.6)
        private const val SQRT_2 = 1.4142135623730951 // sqrt(2)
        private const val SQRT_3 = 1.7320508075688772 // sqrt(3)

        @JvmStatic
        fun arcCosineArguments() = listOf(
            Arguments.of(0.1, ACOS_0_1),
            Arguments.of(-0.1, ACOS_N_0_1),
            Arguments.of(-0.5, 2 * PI / 3),
            Arguments.of(0.5, PI / 3),
            Arguments.of((SQRT_3 - 1) / (2 * SQRT_2), 5 * PI / 12),
            Arguments.of(-(SQRT_3 - 1) / (2 * SQRT_2), 7 * PI / 12),
            Arguments.of(0.6, ACOS_0_6),
            Arguments.of(-0.6, ACOS_N_0_6)
        )
    }

}
