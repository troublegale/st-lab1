package itmo.tg

import kotlin.math.*

fun factorial(n: Int): Int {
    if (n < 0) throw IllegalArgumentException("Factorial is not defined for negative values")
    if (n == 0) return 1
    return n * factorial(n - 1)
}

fun arcCosineDecomposition(x: Double, steps: Int): Double {
    if (x < -1.0 || x > 1.0) throw IllegalArgumentException("Arc cosine is undefined outside of [-1; 1]")
    if (steps < 1) throw IllegalArgumentException("Minimal steps is 1")
    val const = PI / 2.0
    var sum = 0.0
    for (n in 0 until steps) {
        val coefficientNumerator = factorial(2*n).toDouble()
        val coefficientDenominator = 2.0.pow(2*n) * factorial(n).toDouble().pow(2)
        val argumentNumerator = x.pow(2*n + 1)
        val argumentDenominator = (2*n + 1).toDouble()
        sum += coefficientNumerator / coefficientDenominator * argumentNumerator / argumentDenominator
    }
    return const - sum
}
