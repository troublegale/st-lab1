import itmo.tg.DFS
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class TaskTwoTests {

    @Test
    fun `DFS can't be performed on an empty graph`() {
        assertThrows<IllegalArgumentException> {
            DFS().dfs(3, mapOf(), mutableSetOf())
        }
    }

    @Test
    fun `DFS has to start from a vertex of a graph`() {
        assertThrows<IllegalArgumentException> {
            DFS().dfs(
                5, mapOf(Pair(1, listOf(2)), Pair(2, listOf(1))),
                mutableSetOf()
            )
        }
    }

    @ParameterizedTest
    @MethodSource("dfsCases")
    fun `DFS algorithm should work out properly`(
        inputVertex: Int, inputGraph: MutableMap<Int, List<Int>>,
        expected: List<String>
    ) {
        val dfs = DFS()
        val visited = mutableSetOf<Int>()
        dfs.dfs(inputVertex, inputGraph, visited)
        assertEquals(expected, dfs.moves)
    }

    companion object {

        private val graph1 = mutableMapOf(
            Pair(0, listOf(1)),
            Pair(1, listOf(2)),
            Pair(2, listOf())
        )

        private val result1 = listOf(
            v(0),
            m(0, 1),
            v(1),
            m(1, 2),
            v(2),
            f(2),
            f(1),
            f(0)
        )

        private val graph2 = mutableMapOf(
            Pair(0, listOf<Int>()),
            Pair(1, listOf()),
            Pair(2, listOf()),
            Pair(3, listOf()),
            Pair(4, listOf())
        )

        private val result2 = listOf(
            v(3),
            f(3)
        )

        private val graph3 = mutableMapOf(
            Pair(0, listOf(4)),
            Pair(1, listOf(0, 3, 5, 6)),
            Pair(2, listOf(1)),
            Pair(3, listOf(5)),
            Pair(4, listOf()),
            Pair(5, listOf()),
            Pair(6, listOf(4, 5, 7)),
            Pair(7, listOf()),
        )

        private val result3 = listOf(
            v(1),
            m(1, 0),
            v(0),
            m(0, 4),
            v(4),
            f(4),
            f(0),
            m(1, 3),
            v(3),
            m(3, 5),
            v(5),
            f(5),
            f(3),
            m(1, 6),
            v(6),
            m(6, 7),
            v(7),
            f(7),
            f(6),
            f(1)
        )

        private val graph4 = mutableMapOf(
            Pair(0, listOf(1)),
            Pair(1, listOf(5)),
            Pair(2, listOf(1)),
            Pair(3, listOf(5)),
            Pair(4, listOf(7)),
            Pair(5, listOf(3)),
            Pair(6, listOf(5)),
            Pair(7, listOf(5)),
        )

        private val result4 = listOf(
            v(0),
            m(0, 1),
            v(1),
            m(1, 5),
            v(5),
            m(5, 3),
            v(3),
            f(3),
            f(5),
            f(1),
            f(0)
        )

        private fun v(i: Int) = "visited $i"
        private fun m(i: Int, j: Int) = "moving from $i to adjacent $j"
        private fun f(i: Int) = "returning from $i"

        @JvmStatic
        fun dfsCases() = listOf(
            Arguments.of(0, graph1, result1),
            Arguments.of(3, graph2, result2),
            Arguments.of(1, graph3, result3),
            Arguments.of(0, graph4, result4),
        )
    }

}