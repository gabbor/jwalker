package epieffe.solver.heuristic;

import epieffe.solver.problem.config.NQueens;

public class NQueensHeuristic {

    /**
     * Ritorna il numero di minaccie in questa configurazione.
     * nb: si tratta di un'euristica consistente!
     */
    public static int numThreats(NQueens problem) {
        int h = 0;
        for (int col = 0; col < problem.getLength(); col++) {
            int colVal = problem.getPos(col);
            for (int i = col + 1; i < problem.getLength(); i++) {
                int val = problem.getPos(i);
                int dist = i - col;
                if (val == colVal || val == colVal - dist || val == colVal + dist) {
                    h++;
                }
            }
        }
        return h;
    }
}
