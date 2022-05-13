package epieffe.solver.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import epieffe.solver.heuristic.Heuristic;
import epieffe.solver.problem.Move;
import epieffe.solver.problem.Problem;

class Searches {

    private static final Random random = new Random();

    static <T> T steepestDescent(Problem<T> problem, T config, Heuristic<T> h, int maxSides) {
        T sol = null;
        T localBest = config;
        int localBestH = h.eval(config);
        int countSides = 0;
        while (sol == null) {
            int oldBestH = localBestH;
            List<Move<T>> moveList = problem.getMoves(localBest);
            List<T> bestMoveList = new ArrayList<>();
            for (Move<T> m : moveList) {
                T newProblem = m.config;
                int newH = h.eval(newProblem);
                if (newH <= localBestH) {
                    if (newH < localBestH) {
                        localBestH = newH;
                        bestMoveList.clear();
                    }
                    bestMoveList.add(newProblem);
                }
            }
            if (!bestMoveList.isEmpty()) {
                int randomIndex = random.nextInt(bestMoveList.size());
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
}
