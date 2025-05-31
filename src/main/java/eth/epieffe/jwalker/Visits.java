package eth.epieffe.jwalker;

import eth.epieffe.jwalker.algorithm.AStar;
import eth.epieffe.jwalker.algorithm.BFS;
import eth.epieffe.jwalker.algorithm.GreedyBestFirst;

/**
 * Created by user on 05/03/17.
 */
public class Visits {

    public static <T> Visit<T> aStar(Problem<T> problem, Heuristic<T> heuristic) {
        return new AStar<>(problem, heuristic);
    }

    public static <T> Visit<T> aStar(Problem<T> problem, Heuristic<T> heuristic, int hmul) {
        return new AStar<>(problem, heuristic, hmul);
    }

    public static <T> Visit<T> greedyBestFirst(Problem<T> problem, Heuristic<T> heuristic) {
        return new GreedyBestFirst<>(problem, heuristic);
    }

    public static <T> Visit<T> bfs(Problem<T> problem) {
        return new BFS<>(problem);
    }

    public static <T> Visit<T> dijkstra(Problem<T> problem) {
        return new AStar<>(problem, c -> 0);
    }
}
