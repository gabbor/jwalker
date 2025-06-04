package eth.epieffe.jwalker.npuzzle;

public class NPuzzleHeuristic {

    /**
     * Ritorna la somma delle distanze manhattan di ogni cella
     * nb: è un'euristica consistente!!
     */
    public static int manhattanDistance(NPuzzle status) {
        int h = 0;
        for (byte i = 0; i < status.size(); i++) {
            for (byte j = 0; j < status.size(); j++) {
                int val = status.cell(i, j);
                if (val <= 0) {
                    int length = status.size();
                    val = length * length;
                }
                int properJ = val;
                int properI = 0;
                while (properJ > status.size()) {
                    properJ -= status.size();
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
        for (byte i = 0; i < status.size(); i++) {
            for (byte j = 0; j < status.size(); j++) {
                if (i == status.size() - 1 && j == status.size() - 1) {
                    if (status.cell(i, j) > 0) {
                        h++;
                    }
                } else if (status.cell(i, j) != status.size() * i + j + 1) {
                    h++;
                }
            }
        }
        return h;
    }
}
