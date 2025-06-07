package eth.epieffe.jwalker.algorithm;

import eth.epieffe.jwalker.Edge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class Util {

    private Util() {}

    static <N> List<Edge<N>> buildPath(Node<N> node) {
        List<Edge<N>> edges = new ArrayList<>();
        while (node.parent != null) {
            edges.add(node.edge);
            node = node.parent;
        }
        Collections.reverse(edges);
        return edges;
    }
}
