package eth.epieffe.jwalker;

import eth.epieffe.jwalker.algorithm.SteepestDescent;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Created by user on 07/03/17.
 */
public class LocalSearches {

    public static <N> LocalSearch<N> steepestDescent(
            Graph<N> graph,
            Supplier<N> randomNodeSupplier,
            Heuristic<N> heuristic,
            int maxSides
    ) {
        Objects.requireNonNull(randomNodeSupplier);
        return new SteepestDescent<>(graph, randomNodeSupplier, heuristic, maxSides);
    }
}
