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
import epieffe.solver.problem.NQueensProblem;
import epieffe.solver.problem.Problem;
import epieffe.solver.problem.PuzzleProblem;

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

        Problem<NQueens> problem = new NQueensProblem();
        NQueens config = NQueens.newRandomInstance(100);
        NQueens sol = search.start(problem, config, h);
        System.out.println(sol);
        System.out.println("is solved: " + problem.isSolved(sol) );
        System.out.println("conflicts: " + h.eval(sol));

    }

    public static void nQueens() {
        final Heuristic<NQueens> h = HeuristicNQueens::numThreats;

        final Visit aStar = VisitFactory.aStar();
        final Visit bestFirst = VisitFactory.bestFirst();
        final Visit bfs = VisitFactory.bfs();

        final Visit visit = bestFirst;

        Problem<NQueens> problem = new NQueensProblem();
        NQueens config = NQueens.newRandomInstance(40);
        List<Move<NQueens>> moveList = visit.start(problem, config, h);
        //moveList.forEach( s -> System.out.println(s.move) );
        NQueens sol = moveList.get(moveList.size() -1).config;
        System.out.println(sol);
        System.out.println( "is solved: " + problem.isSolved(sol) );
    }

    
    public static void nPuzzle () {
    	
        final Heuristic<NPuzzle> manhattan = NPuzzleHeuristic::manhattanDistance;
        final Heuristic<NPuzzle> outOfPlace = NPuzzleHeuristic::outOfPlace;

        Heuristic<NPuzzle> h = manhattan;

        final Visit aStar = VisitFactory.aStar(1);
        final Visit bestFirst = VisitFactory.bestFirst();
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
        Problem<NPuzzle> problem = new PuzzleProblem();
        NPuzzle config = NPuzzle.newInstance(t1);
        List<Move<NPuzzle>> moveList = visit.start(problem, config, h);
        moveList.forEach( s -> System.out.println(s.move) );
    }
}
