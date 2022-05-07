package epieffe.solver.example;

import epieffe.solver.algorithm.Search;
import epieffe.solver.algorithm.SearchFactory;
import epieffe.solver.algorithm.Visit;
import epieffe.solver.algorithm.VisitFactory;
import epieffe.solver.heuristic.Heuristic;
import epieffe.solver.heuristic.HeuristicNQueens;
import epieffe.solver.problem.*;

import java.util.List;

public class NQueensExample {

    public static void main (String... args) {
        NQueens config = NQueens.newRandomInstance(100);
        solveWithSteepestDescentSearch(config, 100);
    }

    public static void solveWithSteepestDescentSearch(NQueens config, int maxSides) {
        final Heuristic<NQueens> h = HeuristicNQueens::numThreats;
        final Search search = SearchFactory.steepestDescent(100);
        Problem<NQueens> problem = new NQueensProblem();
        NQueens sol = search.start(problem, config, h);
        System.out.println(sol);
        System.out.println("is solved: " + problem.isSolved(sol) );
        System.out.println("conflicts: " + h.eval(sol));

    }

    public static void solveWithBestFirstVisit(NQueens config) {
        final Heuristic<NQueens> h = HeuristicNQueens::numThreats;
        final Visit visit = VisitFactory.bestFirst();
        Problem<NQueens> problem = new NQueensProblem();
        List<Move<NQueens>> moveList = visit.start(problem, config, h);
        //moveList.forEach(s -> System.out.println(s.move));
        NQueens sol = moveList.get(moveList.size() -1).config;
        System.out.println(sol);
        System.out.println("is solved: " + problem.isSolved(sol));
    }
}
