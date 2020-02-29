package epieffe.solver.problem;

import java.util.*;

/**
 * Rappresenta un'istanza del gioco NPuzzle.
 * Il gioco presenta una matrice n*n dove ogni cella contiene un numero da 1 a n*n-1 e una cella e vuota.
 * Le celle adiacenti a quella vuota possono essere spostate al posto di quella vuota in una mossa.
 * Il gioco è risolto quando tutte le celle sono ordinate e la cella vuota sta nell'ultima posizione.
 */
public class NPuzzle implements Problem {
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
    private NPuzzle(int[][] t, int x, int y) {
        table = t;
        emptyX = x;
        emptyY = y;
    }

    /** Restituisce una lista contenente tutte le configurazioni ottenibili
     * facendo una singola mossa a partire dalla configurazione attuale*/
    @Override
    public List<Move> getMoves() {
        List<Move> moveList = new ArrayList<>(4);
        if (emptyY > 0) {
            /*move up*/
            int[][] newTable = copyTable();
            newTable[emptyY][emptyX] = table[emptyY -1][emptyX];
            newTable[emptyY -1][emptyX] = table[emptyY][emptyX];
            NPuzzle newConfig = new NPuzzle(newTable, emptyX, emptyY - 1);
            Move move = new Move("UP", 1, newConfig);
            moveList.add(move);
        }
        if (emptyY < table.length - 1) {
            /*move down*/
            int[][] newTable = copyTable();
            newTable[emptyY][emptyX] = table[emptyY + 1][emptyX];
            newTable[emptyY + 1][emptyX] = table[emptyY][emptyX];
            NPuzzle newConfig = new NPuzzle(newTable, emptyX, emptyY + 1);
            Move move = new Move("DOWN", 1, newConfig);
            moveList.add(move);
        }
        if (emptyX > 0) {
            /*move left*/
            int[][] newTable = copyTable();
            newTable[emptyY][emptyX] = table[emptyY][emptyX - 1];
            newTable[emptyY][emptyX - 1] = table[emptyY][emptyX];
            NPuzzle newConfig = new NPuzzle(newTable, emptyX - 1, emptyY);
            Move  move = new Move("LEFT", 1, newConfig);
            moveList.add(move);
        }
        if (emptyX < table.length - 1) {
            /*move right*/
            int[][] newTable = copyTable();
            newTable[emptyY][emptyX] = table[emptyY][emptyX + 1];
            newTable[emptyY][emptyX + 1] = table[emptyY][emptyX];
            NPuzzle newConfig = new NPuzzle(newTable, emptyX + 1, emptyY);
            Move  move = new Move("RIGHT", 1, newConfig);
            moveList.add(move);
        }
        return moveList;
    }

    /**Ritorna il numero di celle fuori posto.
     * nb: è un'euristica consistente!!*/
    public int outOfPlace() {
        int h = 0;
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                if (i == table.length - 1 && j == table.length -1) {
                    if (table[i][j] > 0) {
                        h++;
                    }
                } else if (table[i][j] != table.length * i + j + 1) {
                    h++;
                }
            }
        }
        return h;
    }

    /**Ritorna la somma delle distanze manhattan di ogni cella
     * nb: è un'euristica consistente!!*/
    public int manhattan() {
        int h = 0;
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                int val = table[i][j];
                if (val <= 0) {
                    int length = table.length;
                    val = length * length;
                }
                int properJ = val;
                int properI = 0;
                while (properJ > table.length) {
                    properJ -= table.length;
                    properI++;
                }
                properJ--;
                h += Math.abs(j - properJ) + Math.abs(i - properI);
            }
        }
        return h;
    }

    @Override
    public boolean isSolved() {
        int v = 1;
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                if (i == table.length - 1 && j == table.length - 1) {
                    if (table[i][j] > 0) {
                        return false;
                    }
                } else if (table[i][j] != v) {
                    return false;
                }
                v++;
            }
        }
        return true;
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

    private int[][] copyTable() {
        int[][] newTable = new int[table.length][table.length];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                newTable[i][j] = table[i][j];
            }
        }
        return newTable;
    }
}