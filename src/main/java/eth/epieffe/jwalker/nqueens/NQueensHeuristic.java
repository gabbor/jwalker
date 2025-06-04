package eth.epieffe.jwalker.nqueens;

public class NQueensHeuristic {

    /**
     * Ritorna il numero di minaccie in questa configurazione.
     * nb: si tratta di un'euristica consistente!
     */
    public static int numThreats(NQueens status) {
        int h = 0;
        for (int col = 0; col < status.size(); col++) {
            int row = status.row(col);
            for (int i = col + 1; i < status.size(); i++) {
                int row2 = status.row(i);
                int dist = i - col;
                if (row2 == row || row2 == row - dist || row2 == row + dist) {
                    h++;
                }
            }
        }
        return h;
    }
}
