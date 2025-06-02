package eth.epieffe.jwalker.gridpathfinding;

import eth.epieffe.jwalker.Move;
import eth.epieffe.jwalker.Visit;
import eth.epieffe.jwalker.Visits;

import java.util.List;

public class GridPathFindingExample {

    /*
        ⬜ ⬜ ⬜ ⬜ ⬛ ⬛ ⬜ ⬜ ⬜ ⬜
        ⬜ ⬛ ⬜ ⬛ ⬛ ⬜ ⬜ ⬛ ⬜ ⬜
        ⬜ ⬛ ⬜ ⬜ ⬜ ⬜ ⬛ ⬛ ⬜ ⬜
        ⬜ ⬛ ⬜ ⬛ ⬛ ⬜ ⬜ ⬜ ⬜ ⬜
        ⬜ ⬛ 🌟 ⬛ ⬜ ⬜ ⬜ ⬛ ⬛ ⬜
        ⬜ ⬜ ⬜ ⬜ ⬛ ⬜ ⬛ ⬜ ⬜ ⬜
        ⬛ ⬛ ⬛ ⬜ ⬛ ⬜ ⬜ ⬜ ⬜ ⬜
        ⬜ ⬜ ⬜ ⬜ ⬜ ⬜ ⬜ ⬛ ⬜ ⬜
        ⬜ ⬜ ⬜ ⬛ ⬛ ⬛ ⬛ ⬛ ⬜ ⬜
        ⬜ ⬜ ⬜ ⬜ ⬜ ⬜ 🎯 ⬜ ⬜ ⬜
    */
    private static final int[][] GRID = {
            {1, 1, 1, 1, 0, 0, 1, 1, 1, 1},
            {1, 0, 1, 0, 0, 1, 1, 0, 1, 1},
            {1, 0, 1, 1, 1, 1, 0, 0, 1, 1},
            {1, 0, 1, 0, 0, 1, 1, 1, 1, 1},
            {1, 0, 1, 0, 1, 1, 1, 0, 0, 1},
            {1, 1, 1, 1, 0, 1, 0, 1, 1, 1},
            {0, 0, 0, 1, 0, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 1},
            {1, 1, 1, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };


    public static void main(String... args) {
        GridPathFindingProblem problem = GridPathFindingProblem.newInstance(GRID, 9, 6);
        solveWithDijkstra(problem);
    }

    public static void solveWithDijkstra(GridPathFindingProblem problem) {
        Visit<GridCell> visit = Visits.dijkstra(problem);
        GridCell start = GridCell.newInstance(problem, 4, 2);
        List<Move<GridCell>> moves = visit.run(start);
        printMoves(problem, start, moves);
    }

    private static void printMoves(GridPathFindingProblem problem, GridCell start, List<Move<GridCell>> moves) {
        System.out.println("Moves length: " + moves.size());
        System.out.println(problem.prettyString(start));
        moves.forEach(m -> System.out.println(problem.prettyString(m.config)));
    }

}