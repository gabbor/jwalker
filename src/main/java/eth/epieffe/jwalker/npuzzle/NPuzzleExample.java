package eth.epieffe.jwalker.npuzzle;

import eth.epieffe.jwalker.Edge;
import eth.epieffe.jwalker.Graph;
import eth.epieffe.jwalker.Visit;
import eth.epieffe.jwalker.Visits;

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

    private static final Graph<NPuzzle> GRAPH = new NPuzzleGraph();

    public static void main(String... args) {
        NPuzzle status = NPuzzle.newInstance(EXAMPLE_1);
        solveWithBestFirstManhattan(status);
    }

    public static void solveWithAStarManhattan(NPuzzle status) {
        solveWithAStarManhattan(status, 1);
    }

    public static void solveWithAStarManhattan(NPuzzle status, int approx) {
        Visit<NPuzzle> visit = Visits.aStar(GRAPH, NPuzzleHeuristic::manhattanDistance, approx);
        List<Edge<NPuzzle>> edges = visit.run(status);
        printMoves(edges);
    }

    public static void solveWithBestFirstManhattan(NPuzzle status) {
        Visit<NPuzzle> visit = Visits.greedyBestFirst(GRAPH, NPuzzleHeuristic::manhattanDistance);
        List<Edge<NPuzzle>> edges = visit.run(status);
        printMoves(edges);
    }

    public static void solveWithBFSManhattan(NPuzzle status) {
        Visit<NPuzzle> visit = Visits.bfs(GRAPH);
        List<Edge<NPuzzle>> edges = visit.run(status);
        printMoves(edges);
    }

    public static void solveWithAStarOutOfPlace(NPuzzle status) {
        solveWithAStarOutOfPlace(status, 1);
    }

    public static void solveWithAStarOutOfPlace(NPuzzle status, int approx) {
        Visit<NPuzzle> visit = Visits.aStar(GRAPH, NPuzzleHeuristic::outOfPlace, approx);
        List<Edge<NPuzzle>> edges = visit.run(status);
        printMoves(edges);
    }

    public static void solveWithBestFirstOutOfPlace(NPuzzle status) {
        Visit<NPuzzle> visit = Visits.greedyBestFirst(GRAPH, NPuzzleHeuristic::outOfPlace);
        List<Edge<NPuzzle>> edges = visit.run(status);
        printMoves(edges);
    }

    public static void solveWithBFSOutOfPlace(NPuzzle status) {
        Visit<NPuzzle> visit = Visits.bfs(GRAPH);
        List<Edge<NPuzzle>> edges = visit.run(status);
        printMoves(edges);
    }

    private static void printMoves(List<Edge<NPuzzle>> edges) {
        edges.forEach(s -> System.out.println(s.label));
    }
}
