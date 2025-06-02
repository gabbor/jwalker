package eth.epieffe.jwalker;

import java.util.List;

/**
 * A {@code Problem} defines a graph that can be traversed by a graph search
 * algorithm to find a node that represent a solution. Methods are provided to
 * check if a given status is a solution, and to retrieve the moves that bring
 * to a given status to its neighbours.<p>
 *
 * A {@code Problem} status represents a node in the graph, it can be of any
 * type and does not need to implement any interface, but it should implement
 * the {@code equals} and {@code hashCode} methods correctly.<p>
 *
 * A {@code Problem} is usually used by a {@link Visit} or a {@link LocalSearch}
 * to search for a solution with various search algorithms like A* or Dijkstra.
 *
 * @param <T> the type of the possible statuses for this problem
 *
 * @see Visit
 * @see LocalSearch
 * @see Move
 * @author  Epifanio Ferrari
 */
public interface Problem<T> {

    /**
     * Returns the moves that bring to the specified status to its neighbours.
     *
     * @param status a problem status
     * @return the moves that bring to the specified status to its neighbours
     * @throws NullPointerException if status is {@code null}
     */
    List<Move<T>> getMoves(T status);

    /**
     * Returns {@code true} if the specified status is a solution.
     *
     * @param status a problem status
     * @return {@code true} if the specified status is a solution
     * @throws NullPointerException if status is {@code null}
     */
    boolean isSolved(T status);
}
