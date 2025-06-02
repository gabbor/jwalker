package eth.epieffe.jwalker.algorithm;

import eth.epieffe.jwalker.Visit;
import eth.epieffe.jwalker.Heuristic;
import eth.epieffe.jwalker.Move;
import eth.epieffe.jwalker.Problem;
import eth.epieffe.jwalker.util.FibonacciHeap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import static eth.epieffe.jwalker.algorithm.Util.buildPath;

public class GreedyBestFirst<T> implements Visit<T> {

    private final Problem<T> problem;

    private final Heuristic<T> heuristic;

    public GreedyBestFirst(Problem<T> problem, Heuristic<T> heuristic) {
        Objects.requireNonNull(problem);
        Objects.requireNonNull(heuristic);
        this.problem = problem;
        this.heuristic = heuristic;
    }

    @Override
    public List<Move<T>> run(T start, Consumer<T> onVisit) {
        FibonacciHeap<T> openSet = new FibonacciHeap<>();
        Map<T, Node<T>> nodes = new HashMap<>();
        openSet.insert(0, start);
        nodes.put(start, new Node<>(null, null));

        while (!openSet.isEmpty()) {
            T current = openSet.deleteMin().getValue();
            Node<T> currentNode = nodes.get(current);
            if (onVisit != null) {
                onVisit.accept(current);
            }
            if (problem.isSolved(current)) {
                return buildPath(currentNode);
            }
            for (Move<T> move : problem.getMoves(current)) {
                Node<T> node = nodes.get(move.status);
                if (node == null) {
                    openSet.insert(heuristic.eval(move.status), move.status);
                    nodes.put(move.status, new Node<>(currentNode, move));
                }
            }
        }
        // No solution found
        return null;
    }
}
