package epieffe.solver.problem;

import epieffe.solver.problem.config.NPuzzle;

import java.util.ArrayList;
import java.util.List;

public class NPuzzleProblem implements Problem<NPuzzle> {

    /**
     * Restituisce una lista contenente tutte le configurazioni ottenibili
     * facendo una singola mossa a partire dalla configurazione attuale.
     */
    @Override
    public List<Move<NPuzzle>> getMoves(NPuzzle config) {
        byte emptyY = config.getEmptyY();
        byte emptyX = config.getEmptyX();
        List<Move<NPuzzle>> moveList = new ArrayList<>(4);
        if (emptyY > 0) {
            /*move up*/
            byte[][] newTable = copyTable(config);
            newTable[emptyY][emptyX] = config.getCell((byte) (emptyY - 1), emptyX);
            newTable[emptyY - 1][emptyX] = config.getCell(emptyY, emptyX);
            NPuzzle newConfig = new NPuzzle(newTable, emptyX, (byte) (emptyY - 1));
            Move<NPuzzle> move = new Move<>("UP", 1, newConfig);
            moveList.add(move);
        }
        if (emptyY < config.getLength() - 1) {
            /*move down*/
            byte[][] newTable = copyTable(config);
            newTable[emptyY][emptyX] = config.getCell((byte) (emptyY + 1), emptyX);
            newTable[emptyY + 1][emptyX] = config.getCell(emptyY, emptyX);
            NPuzzle newConfig = new NPuzzle(newTable, emptyX, (byte) (emptyY + 1));
            Move<NPuzzle> move = new Move<>("DOWN", 1, newConfig);
            moveList.add(move);
        }
        if (emptyX > 0) {
            /*move left*/
            byte[][] newTable = copyTable(config);
            newTable[emptyY][emptyX] = config.getCell(emptyY, (byte) (emptyX - 1));
            newTable[emptyY][emptyX - 1] = config.getCell(emptyY, emptyX);
            NPuzzle newConfig = new NPuzzle(newTable, (byte) (emptyX - 1), emptyY);
            Move<NPuzzle> move = new Move<>("LEFT", 1, newConfig);
            moveList.add(move);
        }
        if (emptyX < config.getLength() - 1) {
            /*move right*/
            byte[][] newTable = copyTable(config);
            newTable[emptyY][emptyX] = config.getCell(emptyY, (byte) (emptyX + 1));
            newTable[emptyY][emptyX + 1] = config.getCell(emptyY, emptyX);
            NPuzzle newConfig = new NPuzzle(newTable, (byte) (emptyX + 1), emptyY);
            Move<NPuzzle> move = new Move<>("RIGHT", 1, newConfig);
            moveList.add(move);
        }
        return moveList;
    }

    @Override
    public boolean isSolved(NPuzzle config) {
        int length = config.getLength();
        int v = 1;
        for (byte i = 0; i < length; i++) {
            for (byte j = 0; j < length; j++) {
                if (i == length - 1 && j == length - 1) {
                    if (config.getCell(i, j) > 0) {
                        return false;
                    }
                } else if (config.getCell(i, j) != v) {
                    return false;
                }
                v++;
            }
        }
        return true;
    }

    private byte[][] copyTable(NPuzzle config) {
        int length = config.getLength();
        byte[][] newTable = new byte[length][length];
        for (byte i = 0; i < length; i++) {
            for (byte j = 0; j < length; j++) {
                newTable[i][j] = config.getCell(i, j);
            }
        }
        return newTable;
    }
}
