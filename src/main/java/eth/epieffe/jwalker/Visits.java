package eth.epieffe.jwalker;

import eth.epieffe.jwalker.algorithm.AStar;
import eth.epieffe.jwalker.algorithm.BFS;
import eth.epieffe.jwalker.algorithm.GreedyBestFirst;

/**
 * Created by user on 05/03/17.
 */
public class Visits {

    public static <N> Visit<N> aStar(Graph<N> graph, Heuristic<N> heuristic) {
        return new AStar<>(graph, heuristic);
    }

    public static <N> Visit<N> aStar(Graph<N> graph, Heuristic<N> heuristic, int hmul) {
        return new AStar<>(graph, heuristic, hmul);
    }

    public static <N> Visit<N> greedyBestFirst(Graph<N> graph, Heuristic<N> heuristic) {
        return new GreedyBestFirst<>(graph, heuristic);
    }

    public static <N> Visit<N> bfs(Graph<N> graph) {
        return new BFS<>(graph);
    }

    public static <N> Visit<N> dijkstra(Graph<N> graph) {
        return new AStar<>(graph, c -> 0);
    }
}
