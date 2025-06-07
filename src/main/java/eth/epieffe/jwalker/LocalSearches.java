package eth.epieffe.jwalker;

import eth.epieffe.jwalker.algorithm.SteepestDescent;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Factory methods for {@link LocalSearch} classes.
 *
 * @see LocalSearch
 * @see Graph
 * @author Epifanio Ferrari
 */
public final class LocalSearches {

    private LocalSearches() {}

    /**
     * Creates a {@link LocalSearch} that implements the <i>Steepest descent</i> algorithm.<p>
     *
     * <i>Steepest descent</i> starts from a random node in the {@link Graph}, selects one
     * of its neighbours with the lowest heuristic evaluation, if the heuristic for that
     * neighbour is less then or equal the heuristic for the current node, then the neighbour
     * is selected as the current node and the process is repeated, until a local optimal
     * node is found.<p>
     *
     * To avoid infinite loops, selecting a node with the heuristic evaluation equal to the
     * current node can only be done a certain amount of times, regulated by the {@code maxSides}
     * parameter.
     *
     * @param graph a {@link Graph} instance
     * @param randomNodeSupplier a supplier that generates a random node in graph
     * @param heuristic a heuristic for the nodes in graph
     * @param maxSides maximum number of times that selecting a node with the same
     *                 heuristic as the current node is allowed
     * @return a local search that traverses the provided graph with the <i>Steepest descent</i> algorithm
     */
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
