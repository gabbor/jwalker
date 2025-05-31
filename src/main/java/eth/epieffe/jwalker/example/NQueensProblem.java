package eth.epieffe.jwalker.example;

import eth.epieffe.jwalker.Move;
import eth.epieffe.jwalker.Problem;

import java.util.ArrayList;
import java.util.List;

public class NQueensProblem implements Problem<NQueens> {

    @Override
    public List<Move<NQueens>> getMoves(NQueens config) {
        int length = config.getLength();
        int nMoves = (length - 1) * length;
        List<Move<NQueens>> moveList = new ArrayList<>(nMoves);
        for (int i = 0; i < length; i++) {
            for (int v = 0; v < length; v++) {
                if (v != config.getPos(i)) {
                    int[] newPosArray = new int[length];
                    for (int j = 0; j < length; j++) {
                        if (j == i) {
                            newPosArray[j] = v;
                        } else {
                            newPosArray[j] = config.getPos(j);
                        }
                    }
                    NQueens newConfig = new NQueens(newPosArray);
                    String moveString = Integer.toString(i) + " -> " + Integer.toString(newPosArray[i]);
                    Move<NQueens> move = new Move<>(moveString, 1, newConfig);
                    moveList.add(move);
                }
            }
        }
        return moveList;
    }

    /**
     * Ritorna true se nessuna delle regine nella scacchiera è minacciata
     */
    @Override
    public boolean isSolved(NQueens config) {
        int length = config.getLength();
        for (int col = 0; col < length; col++) {
            int colVal = config.getPos(col);
            for (int i = col + 1; i < length; i++) {
                int val = config.getPos(i);
                int dist = i - col;
                if (val == colVal || val == colVal - dist || val == colVal + dist) {
                    return false;
                }
            }
        }
        return true;
    }
}
