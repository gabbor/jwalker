package epieffe.solver.problem;

import java.util.*;

/**
 * Rappresenta un'istanza del gioco NPuzzle.
 * Il gioco presenta una matrice n*n dove ogni cella contiene un numero da 1 a n*n-1 e una cella e vuota.
 * Le celle adiacenti a quella vuota possono essere spostate al posto di quella vuota in una mossa.
 * Il gioco è risolto quando tutte le celle sono ordinate e la cella vuota sta nell'ultima posizione.
 */
public class NPuzzle {
    private final int[][] table;
    private final int emptyX;
    private final int emptyY;

    public static NPuzzle newInstance(int[][] table) {
        if (table.length < 1 || table[0].length != table.length) {
            throw new IllegalArgumentException("invalid table size");
        }
        int emptyX = -1;
        int emptyY = -1;
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                if (table[i][j] >= table.length * table.length) {
                    throw new IllegalArgumentException("invalid table: cell value too high");
                }
                if (table[i][j] < 1) {
                    if (emptyX >= 0 || emptyY >= 0) {
                        throw new IllegalArgumentException("invalid table: more than one empty cell");
                    }
                    emptyX = j;
                    emptyY = i;
                }
            }
        }
        if (emptyX < 0 || emptyY < 0) {
            throw new IllegalArgumentException("invalid table: no empty cell");
        }
        return new NPuzzle(table, emptyX, emptyY);
    }

    /**@param t: matrice che rappresenta la configurazione
     * @param x: riga della cella vuota
     * @param y: colonna della cella vuota
     * si assume che la matrice rappresenti una configurazione valida
     * e che x e y abbiano valori corretti*/
    public NPuzzle(int[][] t, int x, int y) {
        table = t;
        emptyX = x;
        emptyY = y;
    }


    public int getLength() {
        return table.length;
    }

    public int getCell(int row, int col) {
        return table[row][col];
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof NPuzzle)) {
            return false;
        }
        NPuzzle other = (NPuzzle)o;
        if (other.table.length != table.length) {
            return false;
        }
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                if (table[i][j] != other.table[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    
    public int getEmptyX() {
    	return emptyX;
    }
    
    
    public int getEmptyY() {
    	return emptyY;
    }
    

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(table);
    }

    @Override
    public String toString() {
        String string = "";
        for (int[] line : table) {
            string += Arrays.toString(line) + "\n";
        }
        return string;
    }
}