package eth.epieffe.jwalker.nqueens;

import eth.epieffe.jwalker.Edge;
import eth.epieffe.jwalker.Graph;

import java.util.ArrayList;
import java.util.List;

public class NQueensGraph implements Graph<NQueens> {

    @Override
    public List<Edge<NQueens>> outgoingEdges(NQueens node) {
        int length = node.size();
        int nMoves = (length - 1) * length;
        List<Edge<NQueens>> edgeList = new ArrayList<>(nMoves);
        for (int i = 0; i < length; i++) {
            for (int v = 0; v < length; v++) {
                if (v != node.row(i)) {
                    int[] newPosArray = new int[length];
                    for (int j = 0; j < length; j++) {
                        if (j == i) {
                            newPosArray[j] = v;
                        } else {
                            newPosArray[j] = node.row(j);
                        }
                    }
                    NQueens newConfig = new NQueens(newPosArray);
                    String label = String.format("%d -> %d", i, newPosArray[i]);
                    Edge<NQueens> edge = new Edge<>(label, 1, newConfig);
                    edgeList.add(edge);
                }
            }
        }
        return edgeList;
    }

    /**
     * Ritorna true se nessuna delle regine nella scacchiera è minacciata
     */
    @Override
    public boolean isTarget(NQueens node) {
        for (int col = 0; col < node.size(); col++) {
            int colVal = node.row(col);
            for (int i = col + 1; i < node.size(); i++) {
                int val = node.row(i);
                int dist = i - col;
                if (val == colVal || val == colVal - dist || val == colVal + dist) {
                    return false;
                }
            }
        }
        return true;
    }
}
