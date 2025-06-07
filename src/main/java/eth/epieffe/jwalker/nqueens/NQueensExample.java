package eth.epieffe.jwalker.nqueens;

import eth.epieffe.jwalker.Edge;
import eth.epieffe.jwalker.Graph;
import eth.epieffe.jwalker.LocalSearch;
import eth.epieffe.jwalker.LocalSearches;
import eth.epieffe.jwalker.Visit;
import eth.epieffe.jwalker.Visits;
import eth.epieffe.jwalker.Heuristic;

import java.util.List;
import java.util.function.Supplier;

public class NQueensExample {

    public static void main(String... args) {
        int size = 8;
        int maxSides = 100;
        if (args.length > 0) {
            size = Integer.parseInt(args[0]);
            if (args.length > 1) {
                maxSides = Integer.parseInt(args[1]);
            }
        }
        solveWithSteepestDescentSearch(size, maxSides);
    }

    public static void solveWithSteepestDescentSearch(int size) {
        solveWithSteepestDescentSearch(size, 100);
    }

    public static void solveWithSteepestDescentSearch(int size, int maxSides) {
        Graph<NQueens> graph = NQueens.GRAPH;
        Heuristic<NQueens> heuristic = NQueensHeuristic::numThreats;
        Supplier<NQueens> randomNodeSupplier = () -> NQueens.newRandomInstance(size);
        LocalSearch<NQueens> search = LocalSearches.steepestDescent(graph, randomNodeSupplier, heuristic, maxSides);
        NQueens sol = search.run();
        System.out.println(sol);
        System.out.println("is solved: " + graph.isTarget(sol));
        System.out.println("conflicts: " + NQueensHeuristic.numThreats(sol));
    }

    public static void solveWithBestFirstVisit(NQueens status) {
        Graph<NQueens> graph = new NQueensGraph();
        final Visit<NQueens> visit = Visits.greedyBestFirst(graph, NQueensHeuristic::numThreats);
        List<Edge<NQueens>> edgeList = visit.run(status);
        // moveList.forEach(s -> System.out.println(s.move));
        NQueens sol = edgeList.get(edgeList.size() - 1).destination;
        System.out.println(sol);
        System.out.println("is solved: " + graph.isTarget(sol));
    }
}
