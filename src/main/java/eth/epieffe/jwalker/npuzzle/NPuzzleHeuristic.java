package eth.epieffe.jwalker.npuzzle;

public class NPuzzleHeuristic {

    /**
     * Ritorna la somma delle distanze manhattan di ogni cella
     * nb: è un'euristica consistente!!
     */
    public static int manhattanDistance(NPuzzle status) {
        int h = 0;
        for (byte i = 0; i < status.getLength(); i++) {
            for (byte j = 0; j < status.getLength(); j++) {
                int val = status.getCell(i, j);
                if (val <= 0) {
                    int length = status.getLength();
                    val = length * length;
                }
                int properJ = val;
                int properI = 0;
                while (properJ > status.getLength()) {
                    properJ -= status.getLength();
                    properI++;
                }
                properJ--;
                h += Math.abs(j - properJ) + Math.abs(i - properI);
            }
        }
        return h;
    }

    /**
     * Ritorna il numero di celle fuori posto.
     * nb: è un'euristica consistente!!
     */
    public static int outOfPlace(NPuzzle status) {
        int h = 0;
        for (byte i = 0; i < status.getLength(); i++) {
            for (byte j = 0; j < status.getLength(); j++) {
                if (i == status.getLength() - 1 && j == status.getLength() - 1) {
                    if (status.getCell(i, j) > 0) {
                        h++;
                    }
                } else if (status.getCell(i, j) != status.getLength() * i + j + 1) {
                    h++;
                }
            }
        }
        return h;
    }
}
