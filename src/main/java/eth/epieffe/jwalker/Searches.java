package eth.epieffe.jwalker;

import eth.epieffe.jwalker.algorithm.SteepestDescent;

/**
 * Created by user on 07/03/17.
 */
public class Searches {

    public static <T> Search<T> steepestDescent(Problem<T> problem, Heuristic<T> heuristic, int maxSides) {
        return new SteepestDescent<>(problem, heuristic, maxSides);
    }
}
