package eth.epieffe.jwalker.npuzzle;

import eth.epieffe.jwalker.Move;
import eth.epieffe.jwalker.Problem;

import java.util.ArrayList;
import java.util.List;

public class NPuzzleProblem implements Problem<NPuzzle> {

    /**
     * Restituisce una lista contenente tutte le configurazioni ottenibili
     * facendo una singola mossa a partire dalla configurazione attuale.
     */
    @Override
    public List<Move<NPuzzle>> getMoves(NPuzzle status) {
        List<Move<NPuzzle>> moveList = new ArrayList<>(4);
        if (status.getEmptyY() > 0) {
            // move up
            int newEmptyIndex = (status.getEmptyY() - 1) * status.length + status.getEmptyX();
            moveList.add(new Move<>("UP", 1, swapEmptyCell(status, newEmptyIndex)));
        }
        if (status.getEmptyY() < status.length - 1) {
            // move down
            int newEmptyIndex = (status.getEmptyY() + 1) * status.length + status.getEmptyX();
            moveList.add(new Move<>("DOWN", 1, swapEmptyCell(status, newEmptyIndex)));
        }
        if (status.getEmptyX() > 0) {
            // move left
            int newEmptyIndex = status.getEmptyY() * status.length + (status.getEmptyX() - 1);
            moveList.add(new Move<>("LEFT", 1, swapEmptyCell(status, newEmptyIndex)));
        }
        if (status.getEmptyX() < status.length - 1) {
            // move right
            int newEmptyIndex = status.getEmptyY() * status.length + (status.getEmptyX() + 1);
            moveList.add(new Move<>("RIGHT", 1, swapEmptyCell(status, newEmptyIndex)));
        }

        return moveList;
    }

    @Override
    public boolean isSolved(NPuzzle status) {
        if (status.table[status.table.length -1] > 0) {
            return false;
        }
        for (int i = 1; i < status.table.length; ++i) {
            if (status.table[i -1] != i) {
                return false;
            }
        }
        return true;
    }

    private NPuzzle swapEmptyCell(NPuzzle status, int newEmptyIndex) {
        byte[] newTable = status.table.clone();
        newTable[status.emptyIndex] = status.table[newEmptyIndex];
        newTable[newEmptyIndex] = 0;
        return new NPuzzle(status.length, (byte) newEmptyIndex, newTable);
    }
}
