package eth.epieffe.jwalker.nqueens;

public class NQueensHeuristic {

    /**
     * Ritorna il numero di minaccie in questa configurazione.
     * nb: si tratta di un'euristica consistente!
     */
    public static int numThreats(NQueens status) {
        int h = 0;
        for (int col = 0; col < status.getLength(); col++) {
            int colVal = status.getPos(col);
            for (int i = col + 1; i < status.getLength(); i++) {
                int val = status.getPos(i);
                int dist = i - col;
                if (val == colVal || val == colVal - dist || val == colVal + dist) {
                    h++;
                }
            }
        }
        return h;
    }
}
