package eth.epieffe.jwalker.npuzzle;

import eth.epieffe.jwalker.Edge;
import eth.epieffe.jwalker.Graph;

import java.util.ArrayList;
import java.util.List;

public class NPuzzleGraph implements Graph<NPuzzle> {

    /**
     * Restituisce una lista contenente tutte le configurazioni ottenibili
     * facendo una singola mossa a partire dalla configurazione attuale.
     */
    @Override
    public List<Edge<NPuzzle>> outgoingEdges(NPuzzle node) {
        List<Edge<NPuzzle>> edgeList = new ArrayList<>(4);
        if (node.emptyY() > 0) {
            // move up
            int newEmptyIndex = (node.emptyY() - 1) * node.size + node.emptyX();
            edgeList.add(new Edge<>("UP", 1, swapEmptyCell(node, newEmptyIndex)));
        }
        if (node.emptyY() < node.size - 1) {
            // move down
            int newEmptyIndex = (node.emptyY() + 1) * node.size + node.emptyX();
            edgeList.add(new Edge<>("DOWN", 1, swapEmptyCell(node, newEmptyIndex)));
        }
        if (node.emptyX() > 0) {
            // move left
            int newEmptyIndex = node.emptyY() * node.size + (node.emptyX() - 1);
            edgeList.add(new Edge<>("LEFT", 1, swapEmptyCell(node, newEmptyIndex)));
        }
        if (node.emptyX() < node.size - 1) {
            // move right
            int newEmptyIndex = node.emptyY() * node.size + (node.emptyX() + 1);
            edgeList.add(new Edge<>("RIGHT", 1, swapEmptyCell(node, newEmptyIndex)));
        }

        return edgeList;
    }

    @Override
    public boolean isTarget(NPuzzle node) {
        if (node.table[node.table.length -1] > 0) {
            return false;
        }
        for (int i = 1; i < node.table.length; ++i) {
            if (node.table[i -1] != i) {
                return false;
            }
        }
        return true;
    }

    private NPuzzle swapEmptyCell(NPuzzle status, int newEmptyIndex) {
        byte[] newTable = status.table.clone();
        newTable[status.emptyIndex] = status.table[newEmptyIndex];
        newTable[newEmptyIndex] = 0;
        return new NPuzzle(status.size, (byte) newEmptyIndex, newTable);
    }
}
