package eth.epieffe.jwalker.algorithm;

import eth.epieffe.jwalker.Move;

class Node<T> {
    protected Node<T> parent;
    protected Move<T> move;

    Node(Node<T> parent, Move<T> move) {
        this.parent = parent;
        this.move = move;
    }
}
