package eth.epieffe.jwalker;

import eth.epieffe.jwalker.algorithm.SteepestDescent;

/**
 * Created by user on 07/03/17.
 */
public class LocalSearches {

    public static <N> LocalSearch<N> steepestDescent(Graph<N> graph, Heuristic<N> heuristic, int maxSides) {
        return new SteepestDescent<>(graph, heuristic, maxSides);
    }
}
