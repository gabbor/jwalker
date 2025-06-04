package eth.epieffe.jwalker.npuzzle;

import eth.epieffe.jwalker.Graph;

import java.util.Arrays;

/**
 * Rappresenta un'istanza del gioco NPuzzle.
 * Il gioco presenta una matrice n*n dove ogni cella contiene un numero da 1 a n*n-1 e una cella e vuota.
 * Le celle adiacenti a quella vuota possono essere spostate al posto di quella vuota in una mossa.
 * Il gioco è risolto quando tutte le celle sono ordinate e la cella vuota sta nell'ultima posizione.
 */
public class NPuzzle {

    public static final Graph<NPuzzle> GRAPH = new NPuzzleGraph();

    final byte size;
    final byte emptyIndex;
    final byte[] table;

    public static NPuzzle newInstance(int... nums) {
        if (nums.length > Byte.MAX_VALUE) {
            throw new IllegalArgumentException("Size is too large");
        }
        int length = (int) Math.sqrt(nums.length);
        if (length == 0 || length * length != nums.length) {
            throw new IllegalArgumentException("Invalid size");
        }
        // Find empty cell index
        int emptyIndex = -1;
        for (int i = 0; i < nums.length; ++i) {
            if (nums[i] <= 0) {
                emptyIndex = i;
                break;
            }
        }
        if (emptyIndex == -1) {
            throw new IllegalArgumentException("Missing empty cell");
        }
        byte[] table = new byte[nums.length];
        for (int i = 0; i < nums.length; ++i) {
            table[i] = (byte) nums[i];
        }
        table[emptyIndex] = 0;
        // Each number from 0 to table.length must be present
        boolean[] found = new boolean[table.length];
        for (byte b : table) {
            if (b < 0 || b >= table.length) {
                throw new IllegalArgumentException("Invalid number: " + b);
            }
            found[b] = true;
        }
        for (int i = 0; i < table.length; ++i) {
            if (!found[i]) {
                throw new IllegalArgumentException("Missing number: " + i);
            }
        }

        return new NPuzzle((byte) length, (byte) emptyIndex, table);
    }

    /**
     * @param table: array che rappresenta la configurazione.
     * @param size: dimensione del lato del puzzle (es. 3 per un 3x3).
     * @param emptyIndex: posizione della cella vuota.
     *           Si assume che la matrice rappresenti una configurazione valida
     *           e che emptyIndex sia valorizzato correttamente.
     */
    NPuzzle(byte size, byte emptyIndex, byte[] table) {
        this.size = size;
        this.emptyIndex = emptyIndex;
        this.table = table;
    }

    public byte size() {
        return size;
    }

    public byte cell(int row, int col) {
        return table[row * size + col];
    }

    public int emptyX() {
        return emptyIndex % size;
    }

    public int emptyY() {
        return emptyIndex / size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NPuzzle)) return false;
        NPuzzle other = (NPuzzle) o;
        return Arrays.equals(this.table, other.table);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(table);
    }

    @Override
    public String toString() {
        return Arrays.toString(table);
    }
}
