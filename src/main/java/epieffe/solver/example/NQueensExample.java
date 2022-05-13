package epieffe.solver.example;

import epieffe.solver.algorithm.Search;
import epieffe.solver.algorithm.SearchFactory;
import epieffe.solver.algorithm.Visit;
import epieffe.solver.algorithm.VisitFactory;
import epieffe.solver.heuristic.Heuristic;
import epieffe.solver.heuristic.NQueensHeuristic;
import epieffe.solver.problem.*;
import epieffe.solver.problem.config.NQueens;

import java.util.List;

public class NQueensExample {

    public static void main(String... args) {
        int size = 100;
        int maxSides = 100;
        if (args.length > 0) {
            size = Integer.parseInt(args[0]);
            if (args.length > 1) {
                maxSides = Integer.parseInt(args[1]);
            }
        }
        NQueens config = NQueens.newRandomInstance(size);
        solveWithSteepestDescentSearch(config, maxSides);
    }

    public static void solveWithSteepestDescentSearch(int size) {
        solveWithSteepestDescentSearch(size, 100);
    }

    public static void solveWithSteepestDescentSearch(int size, int maxSides) {
        NQueens config = NQueens.newRandomInstance(size);
        solveWithSteepestDescentSearch(config, maxSides);
    }

    public static void solveWithSteepestDescentSearch(NQueens config, int maxSides) {
        final Heuristic<NQueens> h = NQueensHeuristic::numThreats;
        final Search search = SearchFactory.steepestDescent(maxSides);
        Problem<NQueens> problem = new NQueensProblem();
        NQueens sol = search.start(problem, config, h);
        System.out.println(sol);
        System.out.println("is solved: " + problem.isSolved(sol));
        System.out.println("conflicts: " + h.eval(sol));
    }

    public static void solveWithBestFirstVisit(NQueens config) {
        final Heuristic<NQueens> h = NQueensHeuristic::numThreats;
        final Visit visit = VisitFactory.bestFirst();
        Problem<NQueens> problem = new NQueensProblem();
        List<Move<NQueens>> moveList = visit.start(problem, config, h);
        // moveList.forEach(s -> System.out.println(s.move));
        NQueens sol = moveList.get(moveList.size() - 1).config;
        System.out.println(sol);
        System.out.println("is solved: " + problem.isSolved(sol));
    }
}
