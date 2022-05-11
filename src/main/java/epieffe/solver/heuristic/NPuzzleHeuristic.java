package epieffe.solver.heuristic;

import epieffe.solver.problem.config.NPuzzle;

public class NPuzzleHeuristic {

	/**
     * Ritorna la somma delle distanze manhattan di ogni cella
     * nb: è un'euristica consistente!!
     * */
	public static int manhattanDistance(NPuzzle problem) {
		int h = 0;
        for (byte i = 0; i < problem.getLength(); i++) {
            for (byte j = 0; j < problem.getLength(); j++) {
            	int val = problem.getCell(i, j);
                if (val <= 0) {
                    int length = problem.getLength();
                    val = length * length;
                }
                int properJ = val;
                int properI = 0;
                while (properJ > problem.getLength()) {
                    properJ -= problem.getLength();
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
     * */
	public static int outOfPlace(NPuzzle problem) {
        int h = 0;
        for (byte i = 0; i < problem.getLength(); i++) {
            for (byte j = 0; j < problem.getLength(); j++) {
                if (i == problem.getLength() - 1 && j == problem.getLength() -1) {
                    if (problem.getCell(i, j) > 0) {
                        h++;
                    }
                } else if (problem.getCell(i, j) != problem.getLength() * i + j + 1) {
                    h++;
                }
            }
        }
        return h;
    }

}
