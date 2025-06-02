package eth.epieffe.jwalker.algorithm;

import eth.epieffe.jwalker.Move;
import eth.epieffe.jwalker.Problem;
import eth.epieffe.jwalker.Visit;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Consumer;

import static eth.epieffe.jwalker.algorithm.Util.buildPath;

public class BFS<T> implements Visit<T> {

    private final Problem<T> problem;

    public BFS(Problem<T> problem) {
        Objects.requireNonNull(problem);
        this.problem = problem;
    }

    @Override
    public List<Move<T>> run(T start, Consumer<T> onVisit) {
        Queue<T> frontier = new ArrayDeque<>();
        Map<T, Node<T>> nodes = new HashMap<>();
        frontier.add(start);
        nodes.put(start, new Node<>(null, null));
        while (!frontier.isEmpty()) {
            T current = frontier.poll();
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
                    frontier.add(move.status);
                    nodes.put(move.status, new Node<>(currentNode, move));
                }
            }
        }
        // No solution found
        return null;
    }
}
