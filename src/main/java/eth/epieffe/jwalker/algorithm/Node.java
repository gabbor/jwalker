package eth.epieffe.jwalker.algorithm;

import eth.epieffe.jwalker.Edge;

class Node<N> {
    protected Node<N> parent;
    protected Edge<N> edge;

    Node(Node<N> parent, Edge<N> edge) {
        this.parent = parent;
        this.edge = edge;
    }
}
