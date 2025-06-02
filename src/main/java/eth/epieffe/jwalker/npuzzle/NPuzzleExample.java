package eth.epieffe.jwalker.npuzzle;

import eth.epieffe.jwalker.Visit;
import eth.epieffe.jwalker.Visits;
import eth.epieffe.jwalker.Move;
import eth.epieffe.jwalker.Problem;

import java.util.List;

public class NPuzzleExample {

    private static final int[] EXAMPLE_1 = {
            7,  1,  2,
            4,  8,  3,
            5,  0,  6
    };

    private static final int[] EXAMPLE_2 = {// easy
            2,  3,  4,  8,
            6, 12, 11, 15,
            1,  7, 10, 14,
            9,  5, 13,  0
    };
    private static final int[] EXAMPLE_3 = {// hard
            8, 12, 10,  7,
            3, 14,  6, 13,
            4,  9,  5,  2,
            1, 15, 11, -1
    };

    private static final Problem<NPuzzle> problem = new NPuzzleProblem();

    public static void main(String... args) {
        NPuzzle status = NPuzzle.newInstance(EXAMPLE_1);
        solveWithBestFirstManhattan(status);
    }

    public static void solveWithAStarManhattan(NPuzzle status) {
        solveWithAStarManhattan(status, 1);
    }

    public static void solveWithAStarManhattan(NPuzzle status, int approx) {
        Visit<NPuzzle> visit = Visits.aStar(problem, NPuzzleHeuristic::manhattanDistance, approx);
        List<Move<NPuzzle>> moves = visit.run(status);
        printMoves(moves);
    }

    public static void solveWithBestFirstManhattan(NPuzzle status) {
        Visit<NPuzzle> visit = Visits.greedyBestFirst(problem, NPuzzleHeuristic::manhattanDistance);
        List<Move<NPuzzle>> moves = visit.run(status);
        printMoves(moves);
    }

    public static void solveWithBFSManhattan(NPuzzle status) {
        Visit<NPuzzle> visit = Visits.bfs(problem);
        List<Move<NPuzzle>> moves = visit.run(status);
        printMoves(moves);
    }

    public static void solveWithAStarOutOfPlace(NPuzzle status) {
        solveWithAStarOutOfPlace(status, 1);
    }

    public static void solveWithAStarOutOfPlace(NPuzzle status, int approx) {
        Visit<NPuzzle> visit = Visits.aStar(problem, NPuzzleHeuristic::outOfPlace, approx);
        List<Move<NPuzzle>> moves = visit.run(status);
        printMoves(moves);
    }

    public static void solveWithBestFirstOutOfPlace(NPuzzle status) {
        Visit<NPuzzle> visit = Visits.greedyBestFirst(problem, NPuzzleHeuristic::outOfPlace);
        List<Move<NPuzzle>> moves = visit.run(status);
        printMoves(moves);
    }

    public static void solveWithBFSOutOfPlace(NPuzzle status) {
        Visit<NPuzzle> visit = Visits.bfs(problem);
        List<Move<NPuzzle>> moves = visit.run(status);
        printMoves(moves);
    }

    private static void printMoves(List<Move<NPuzzle>> moves) {
        moves.forEach(s -> System.out.println(s.move));
    }
}
