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

public class AStar<T> implements Visit<T> {

    private final Problem<T> problem;

    private final Heuristic<T> heuristic;

    private final double hmul;

    public AStar(Problem<T> problem, Heuristic<T> heuristic) {
        this(problem, heuristic, 1);
    }

    public AStar(Problem<T> problem, Heuristic<T> heuristic, double hmul) {
        Objects.requireNonNull(problem);
        Objects.requireNonNull(heuristic);
        if (hmul < 1) {
            throw new IllegalArgumentException("Argument hmul must be >= 1");
        }
        this.problem = problem;
        this.heuristic = heuristic;
        this.hmul = hmul;
    }

    @Override
    public List<Move<T>> run(T start, Consumer<T> onVisit) {
        FibonacciHeap<T> openSet = new FibonacciHeap<>();
        Map<T, ANode<T>> nodes = new HashMap<>();
        FibonacciHeap.Handle<T> startHandle = openSet.insert(0, start);
        ANode<T> startNode = new ANode<>(null, null, startHandle, 0, heuristic.eval(start));
        nodes.put(start, startNode);

        while (!openSet.isEmpty()) {
            T current = openSet.deleteMin().getValue();
            ANode<T> currentNode = nodes.get(current);
            currentNode.expand();
            if (onVisit != null) {
                onVisit.accept(current);
            }
            if (problem.isSolved(current)) {
                return buildPath(currentNode);
            }
            for (Move<T> move : problem.getMoves(current)) {
                double g = currentNode.g + move.cost;
                ANode<T> node = nodes.get(move.config);
                if (node == null) {
                    double h = heuristic.eval(move.config);
                    double f = g + (h * hmul);
                    FibonacciHeap.Handle<T> handle = openSet.insert(f, move.config);
                    node = new ANode<>(currentNode, move, handle, g, h);
                    nodes.put(move.config, node);
                } else if (!node.isExpanded() && g < node.g) {
                    double f = g + (node.h * hmul);
                    node.g = g;
                    node.parent = currentNode;
                    node.move = move;
                    node.handle.decreaseKey(f);
                }
            }
        }
        // No solution found
        return null;
    }

    private static class ANode<T> extends Node<T> {
        FibonacciHeap.Handle<T> handle;
        double g;
        double h;

        ANode(ANode<T> parent, Move<T> move, FibonacciHeap.Handle<T> handle, double g, double h) {
            super(parent, move);
            this.handle = handle;
            this.g = g;
            this.h = h;
        }

        void expand() {
            handle = null;
        }

        boolean isExpanded() {
            return handle == null;
        }
    }
}
