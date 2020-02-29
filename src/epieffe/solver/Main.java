package epieffe.solver;
import java.util.List;

import epieffe.solver.algorithm.Search;
import epieffe.solver.algorithm.SearchFactory;
import epieffe.solver.algorithm.Visit;
import epieffe.solver.algorithm.VisitFactory;
import epieffe.solver.heuristic.Heuristic;
import epieffe.solver.heuristic.HeuristicNQueens;
import epieffe.solver.heuristic.NPuzzleHeuristic;
import epieffe.solver.problem.Move;
import epieffe.solver.problem.NPuzzle;
import epieffe.solver.problem.NQueens;
import epieffe.solver.problem.Problem;

/**
 * Created by user on 04/03/17.
 */
public class Main {
    public static void main (String... args) {
        //nQueens();
        nPuzzle();
        //queenSearch();
    }

    public static void queenSearch() {
        final Heuristic<NQueens> h = HeuristicNQueens::numThreats;
        final Search search = SearchFactory.steepestDescent(100);

        NQueens prob = NQueens.newRandomInstance(100);
        NQueens sol = search.start(prob, h);
        System.out.println(sol);
        System.out.println("is solved: " + sol.isSolved());
        System.out.println("conflicts: " + h.eval(sol));

    }

    public static void nQueens() {
        final Heuristic<NQueens> h = HeuristicNQueens::numThreats;

        final Visit aStar = VisitFactory.aStar(h);
        final Visit bestFirst = VisitFactory.bestFirst(h);
        final Visit bfs = VisitFactory.bfs();

        final Visit visit = bestFirst;

        Problem p = NQueens.newRandomInstance(40);
        List<Move> moveList = visit.start(p);
        //moveList.forEach( s -> System.out.println(s.move) );
        Problem sol = moveList.get(moveList.size() -1).config;
        System.out.println(sol);
        System.out.println("is solved: " + sol.isSolved());
    }

    public static void nPuzzle () {

        final Heuristic<NPuzzle> manhattan = NPuzzleHeuristic::manhattanDistance;
        final Heuristic<NPuzzle> outOfPlace = NPuzzleHeuristic::outOfPlace;

        Heuristic<NPuzzle> h = manhattan;

        final Visit aStar = VisitFactory.aStar(h, 1);
        final Visit bestFirst = VisitFactory.bestFirst(h);
        final Visit bfs = VisitFactory.bfs();

        Visit visit = bestFirst;


        int[][] t1 = {
                {7,  1,  2},
                {4,  8,  3},
                {5,  -1, 6}
        };
        int[][] t2 = {// easy
                { 2,  3,  4,  8},
                { 6, 12, 11, 15},
                { 1,  7, 10, 14},
                { 9,  5, 13, -1}
        };
        int[][] t3 = {// hard
                { 8, 12, 10,  7},
                { 3, 14,  6, 13},
                { 4,  9,  5,  2},
                { 1, 15, 11, -1}
        };
        Problem p = NPuzzle.newInstance(t1);
        List<Move> moveList = visit.start(p);
        moveList.forEach( s -> System.out.println(s.move) );
    }
}
