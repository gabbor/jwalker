package eth.epieffe.jwalker.example;

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
    public List<Move<NPuzzle>> getMoves(NPuzzle config) {
        List<Move<NPuzzle>> moveList = new ArrayList<>(4);
        if (config.getEmptyY() > 0) {
            // move up
            int newEmptyIndex = (config.getEmptyY() - 1) * config.length + config.getEmptyX();
            moveList.add(new Move<>("UP", 1, swapEmptyCell(config, newEmptyIndex)));
        }
        if (config.getEmptyY() < config.length - 1) {
            // move down
            int newEmptyIndex = (config.getEmptyY() + 1) * config.length + config.getEmptyX();
            moveList.add(new Move<>("DOWN", 1, swapEmptyCell(config, newEmptyIndex)));
        }
        if (config.getEmptyX() > 0) {
            // move left
            int newEmptyIndex = config.getEmptyY() * config.length + (config.getEmptyX() - 1);
            moveList.add(new Move<>("LEFT", 1, swapEmptyCell(config, newEmptyIndex)));
        }
        if (config.getEmptyX() < config.length - 1) {
            // move right
            int newEmptyIndex = config.getEmptyY() * config.length + (config.getEmptyX() + 1);
            moveList.add(new Move<>("RIGHT", 1, swapEmptyCell(config, newEmptyIndex)));
        }

        return moveList;
    }

    @Override
    public boolean isSolved(NPuzzle config) {
        if (config.table[config.table.length -1] > 0) {
            return false;
        }
        for (int i = 1; i < config.table.length; ++i) {
            if (config.table[i -1] != i) {
                return false;
            }
        }
        return true;
    }

    private NPuzzle swapEmptyCell(NPuzzle config, int newEmptyIndex) {
        byte[] newTable = config.table.clone();
        newTable[config.emptyIndex] = config.table[newEmptyIndex];
        newTable[newEmptyIndex] = 0;
        return new NPuzzle(config.length, (byte) newEmptyIndex, newTable);
    }
}
