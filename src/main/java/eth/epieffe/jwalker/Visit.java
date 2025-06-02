package eth.epieffe.jwalker;

import java.util.List;
import java.util.function.Consumer;

/**
 * A pathfinding algorithm that traverses the graph defined by a {@link Problem}
 * to find a path from an initial status to a status that is a solution for the
 * problem.<p>
 *
 * Optionally, users can provide a {@link Consumer} that will be called on each
 * explored status when it is visited.<p>
 *
 * The {@link Visits} class provides factory methods for various pathfinding
 * algorithms such as Dijkstra, A*, Best-first and Breadth-first search.
 *
 * @param <T> the type of the statuses of the underlying {@link Problem}
 *
 * @see Problem
 * @see Move
 * @see Visits
 * @author  Epifanio Ferrari
 */
public interface Visit<T> {

    /**
     * Traverses the graph defined by the underlying {@link Problem}
     * to find a path from the specified status to a status that is
     * a solution for the problem.
     *
     * @param status a problem status
     * @return a list of moves that bring from the specified status to a
     * solution, or null if no valid path is found
     * @throws NullPointerException if status is {@code null}
     */
    default List<Move<T>> run(T status) {
        return run(status, null);
    }

    /**
     * Traverses the graph defined by the underlying {@link Problem}
     * to find a path from the specified status to a status that is
     * a solution for the problem.
     *
     * @param status a problem status
     * @param onVisit a callback that will be executed on each explored
     * status when it is visited
     * @return a list of moves that bring from the specified status to a
     * solution, or null if no valid path is found
     * @throws NullPointerException if status is {@code null}
     */
    List<Move<T>> run(T status, Consumer<T> onVisit);

    /**
     * Returns the underlying problem of this {@code Visit}.
     *
     * @return the underlying problem of this visit
     */
    Problem<T> getProblem();
}
