package itmo.tg

class DFS {

    val moves: MutableList<String> = mutableListOf()

    fun dfs(
        v: Int, graph: Map<Int, List<Int>>, visited: MutableSet<Int>
    ) {
        if (graph.isEmpty()) throw IllegalArgumentException(
            "Graph is empty")
        if (v !in graph.keys) throw IllegalArgumentException(
            "Graph doesn't have a vertex #$v")
        moves.add("visited $v")
        visited.add(v)
        for (u in graph.getValue(v)) {
            if (u !in visited) {
                moves.add("moving from $v to adjacent $u")
                dfs(u, graph, visited)
            }
        }
        moves.add("returning from $v")
    }

}
