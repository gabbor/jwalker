package eth.epieffe.jwalker.algorithm;

import eth.epieffe.jwalker.Move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Util {

    private Util() {/* Must not be instantiated */}

    protected static <T> List<Move<T>> buildPath(Node<T> node) {
        List<Move<T>> moves = new ArrayList<>();
        while (node.parent != null) {
            moves.add(node.move);
            node = node.parent;
        }
        Collections.reverse(moves);
        return moves;
    }
}
