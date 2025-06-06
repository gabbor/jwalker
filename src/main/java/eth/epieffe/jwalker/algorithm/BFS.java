package eth.epieffe.jwalker.algorithm;

import eth.epieffe.jwalker.Graph;
import eth.epieffe.jwalker.Edge;
import eth.epieffe.jwalker.Visit;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Consumer;

import static eth.epieffe.jwalker.algorithm.Util.buildPath;

public class BFS<N> implements Visit<N> {

    private final Graph<N> graph;

    public BFS(Graph<N> graph) {
        this.graph = Objects.requireNonNull(graph);
    }

    @Override
    public List<Edge<N>> run(N start, Consumer<N> onVisit) {
        Queue<N> frontier = new ArrayDeque<>();
        Map<N, Node<N>> nodes = new HashMap<>();
        frontier.add(start);
        nodes.put(start, new Node<>(null, null));
        while (!frontier.isEmpty()) {
            N current = frontier.poll();
            Node<N> currentNode = nodes.get(current);
            if (onVisit != null) {
                onVisit.accept(current);
            }
            if (graph.isTarget(current)) {
                return buildPath(currentNode);
            }
            for (Edge<N> edge : graph.outgoingEdges(current)) {
                Node<N> node = nodes.get(edge.destination);
                if (node == null) {
                    frontier.add(edge.destination);
                    nodes.put(edge.destination, new Node<>(currentNode, edge));
                }
            }
        }
        // No solution found
        return null;
    }

    @Override
    public Graph<N> getGraph() {
        return graph;
    }
}
