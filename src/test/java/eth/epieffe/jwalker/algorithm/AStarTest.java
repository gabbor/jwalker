package eth.epieffe.jwalker.algorithm;

import eth.epieffe.jwalker.Move;
import eth.epieffe.jwalker.Problem;
import eth.epieffe.jwalker.Visit;
import eth.epieffe.jwalker.example.NPuzzle;
import eth.epieffe.jwalker.example.NPuzzleHeuristic;
import org.junit.jupiter.api.Test;

import java.util.List;

import static eth.epieffe.jwalker.algorithm.PathAssertions.assertValidPath;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AStarTest {

    private static final Problem<NPuzzle> nPuzzle = NPuzzle.PROBLEM;

    @Test
    public void test8PuzzleWithManhattanHeuristic() {
        Visit<NPuzzle> visit = new AStar<>(nPuzzle, NPuzzleHeuristic::manhattanDistance);
        byte[][] table = {
            {8,  7,  4},
            {1,  6,  3},
            {2,  5, -1}
        };
        NPuzzle start = NPuzzle.newInstance(table);
        List<Move<NPuzzle>> path = visit.start(start);
        assertValidPath(nPuzzle, start, path);
        assertEquals(22, path.size());
    }

    @Test
    public void test8PuzzleWithOutOfPlaceHeuristic() {
        Visit<NPuzzle> visit = new AStar<>(nPuzzle, NPuzzleHeuristic::outOfPlace);
        byte[][] table = {
            {5,  3,  7},
            {4, -1,  6},
            {1,  2,  8}
        };
        NPuzzle start = NPuzzle.newInstance(table);
        List<Move<NPuzzle>> path = visit.start(start);
        assertValidPath(nPuzzle, start, path);
        assertEquals(22, path.size());
    }

    @Test
    public void test8PuzzleWithDijkstra() {
        Visit<NPuzzle> visit = new AStar<>(nPuzzle, c -> 0);
        byte[][] table = {
            {7,  1,  2},
            {4,  8,  3},
            {5, -1,  6}
        };
        NPuzzle start = NPuzzle.newInstance(table);
        List<Move<NPuzzle>> path = visit.start(start);
        assertValidPath(nPuzzle, start, path);
        assertEquals(13, path.size());
    }

    @Test
    public void test15PuzzleWithHMulAndManhattanHeuristic() {
        Visit<NPuzzle> visit = new AStar<>(nPuzzle, NPuzzleHeuristic::manhattanDistance, 2);
        byte[][] table = {
            {8, 12, 10,  7},
            {3, 14,  6, 13},
            {4,  9,  5,  2},
            {1, 15, 11, -1}
        };
        NPuzzle start = NPuzzle.newInstance(table);
        List<Move<NPuzzle>> path = visit.start(start);
        assertValidPath(nPuzzle, start, path);
        assertEquals(78, path.size());
    }
}
