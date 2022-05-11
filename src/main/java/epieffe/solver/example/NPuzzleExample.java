package epieffe.solver.example;

import epieffe.solver.algorithm.Visit;
import epieffe.solver.algorithm.VisitFactory;
import epieffe.solver.heuristic.Heuristic;
import epieffe.solver.heuristic.NPuzzleHeuristic;
import epieffe.solver.problem.Move;
import epieffe.solver.problem.config.NPuzzle;
import epieffe.solver.problem.Problem;
import epieffe.solver.problem.NPuzzleProblem;

import java.util.List;

public class NPuzzleExample {

    private static final byte[][] EXAMPLE_1 = {
            {7,  1,  2},
            {4,  8,  3},
            {5,  -1, 6}
    };

    private static final byte[][] EXAMPLE_2 = {// easy
            { 2,  3,  4,  8},
            { 6, 12, 11, 15},
            { 1,  7, 10, 14},
            { 9,  5, 13, -1}
    };
    private static final byte[][] EXAMPLE_3 = {// hard
            { 8, 12, 10,  7},
            { 3, 14,  6, 13},
            { 4,  9,  5,  2},
            { 1, 15, 11, -1}
    };

    public static void main(String... args) {
        NPuzzle config = NPuzzle.newInstance(EXAMPLE_1);
        solveWithBestFirstManhattan(config);
    }

    public static void solveWithAStarManhattan(NPuzzle config) {
        solveWithAStarManhattan(config, 1);
    }

    public static void solveWithAStarManhattan(NPuzzle config, int approx) {
        solveAndPrint(config, VisitFactory.aStar(approx), NPuzzleHeuristic::manhattanDistance);
    }

    public static void solveWithBestFirstManhattan(NPuzzle config) {
        solveAndPrint(config, VisitFactory.bestFirst(), NPuzzleHeuristic::manhattanDistance);
    }

    public static void solveWithBFSManhattan(NPuzzle config) {
        solveAndPrint(config, VisitFactory.bfs(), NPuzzleHeuristic::manhattanDistance);
    }

    public static void solveWithAStarOutOfPlace(NPuzzle config) {
        solveWithAStarOutOfPlace(config, 1);
    }

    public static void solveWithAStarOutOfPlace(NPuzzle config, int approx) {
        solveAndPrint(config, VisitFactory.aStar(approx), NPuzzleHeuristic::outOfPlace);
    }

    public static void solveWithBestFirstOutOfPlace(NPuzzle config) {
        solveAndPrint(config, VisitFactory.bestFirst(), NPuzzleHeuristic::outOfPlace);
    }

    public static void solveWithBFSOutOfPlace(NPuzzle config) {
        solveAndPrint(config, VisitFactory.bfs(), NPuzzleHeuristic::outOfPlace);
    }

    private static void solveAndPrint(NPuzzle config, Visit visit, Heuristic<NPuzzle> h) {
        Problem<NPuzzle> problem = new NPuzzleProblem();
        List<Move<NPuzzle>> moveList = visit.start(problem, config, h);
        moveList.forEach(s -> System.out.println(s.move));
    }
}
