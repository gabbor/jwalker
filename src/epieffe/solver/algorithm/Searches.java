package epieffe.solver.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import epieffe.solver.heuristic.Heuristic;
import epieffe.solver.problem.Move;
import epieffe.solver.problem.Problem;

/** */
class Searches {
    private static final Random random = new Random();

    static <T extends Problem> T steepestDescent(T p, Heuristic<T> h, int maxSides) {
    	T sol = null;
    	T localBest = p;
        int localBestH = h.eval(p);
        int countSides = 0;
        while (sol == null) {
            int oldBestH = localBestH;
            List<Move> moveList = localBest.getMoves();
            List<T> bestMoveList = new ArrayList<>();
            for (Move m : moveList) {
                T newProblem = (T) m.config;
                int newH = h.eval(newProblem);
                if (newH <= localBestH) {
                    if (newH < localBestH) {
                        localBestH = newH;
                        bestMoveList.clear();
                    }
                    bestMoveList.add(newProblem);
                }
            }
            if ( !bestMoveList.isEmpty() ) {
                int randomIndex = random.nextInt( bestMoveList.size() );
                localBest = bestMoveList.get(randomIndex);
                if (localBestH == oldBestH) {
                    countSides++;
                    if (countSides >= maxSides) {
                        sol = localBest;
                    }
                }
            } else {
                sol = localBest;
            }
        }
        return sol;
    }

    private static boolean maybeTrue(int p) {
        for (int i = 0; i < p; i++) {
            boolean randBool = random.nextBoolean();
            if (!randBool) {
                return false;
            }
        }
        return true;
    }
}
