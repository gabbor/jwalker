package eth.epieffe.jwalker.gridpathfinding;

import eth.epieffe.jwalker.Edge;
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
        List<Edge<GridCell>> edges = visit.run(start);
        printMoves(problem, start, edges);
    }

    private static void printMoves(GridPathFindingProblem problem, GridCell start, List<Edge<GridCell>> edges) {
        System.out.println("Moves length: " + edges.size());
        System.out.println(problem.prettyString(start));
        edges.forEach(m -> System.out.println(problem.prettyString(m.destination)));
    }

}