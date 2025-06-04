package eth.epieffe.jwalker.algorithm;

import eth.epieffe.jwalker.Edge;
import eth.epieffe.jwalker.Graph;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PathAssertions {

    public static <T> void assertValidPath(Graph<T> graph, T start, List<Edge<T>> path) {
        assertNotNull(path);

        T current = start;
        for (Edge<T> edge : path) {
            boolean valid = graph.outgoingEdges(current).contains(edge);
            assertTrue(valid, "Invalid move");
            current = edge.destination;
        }
        assertTrue(graph.isTarget(current), "Invalid solution");
    }
}
