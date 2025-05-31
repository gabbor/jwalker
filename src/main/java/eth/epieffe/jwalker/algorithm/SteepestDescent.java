package eth.epieffe.jwalker.algorithm;

import eth.epieffe.jwalker.Search;
import eth.epieffe.jwalker.Heuristic;
import eth.epieffe.jwalker.Move;
import eth.epieffe.jwalker.Problem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class SteepestDescent<T> implements Search<T> {

    private final Random random = new Random();

    private final Problem<T> problem;

    private final Heuristic<T> heuristic;

    private final int maxSides;

    public SteepestDescent(Problem<T> problem, Heuristic<T> heuristic, int maxSides) {
        Objects.requireNonNull(problem);
        Objects.requireNonNull(heuristic);
        if (maxSides < 0) {
            throw new IllegalArgumentException("Argument maxSides must not be negative");
        }
        this.problem = problem;
        this.heuristic = heuristic;
        this.maxSides = maxSides;
    }

    @Override
    public T start(T config) {
        T sol = null;
        T localBest = config;
        int localBestH = heuristic.eval(config);
        int countSides = 0;
        while (sol == null) {
            int oldBestH = localBestH;
            List<Move<T>> moveList = problem.getMoves(localBest);
            List<T> bestMoveList = new ArrayList<>();
            for (Move<T> m : moveList) {
                T newProblem = m.config;
                int newH = heuristic.eval(newProblem);
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
