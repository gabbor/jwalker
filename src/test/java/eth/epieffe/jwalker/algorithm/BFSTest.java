package eth.epieffe.jwalker.algorithm;

import eth.epieffe.jwalker.Move;
import eth.epieffe.jwalker.Problem;
import eth.epieffe.jwalker.Visit;
import eth.epieffe.jwalker.gridpathfinding.GridCell;
import eth.epieffe.jwalker.gridpathfinding.GridPathFindingProblem;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static eth.epieffe.jwalker.algorithm.PathAssertions.assertValidPath;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BFSTest {

    public static class TestCase {
        public final Problem<GridCell> problem;
        public final GridCell startCell;
        public final double totalCost;

        public TestCase(Problem<GridCell> problem, GridCell startCell, double totalCost) {
            this.problem = problem;
            this.startCell = startCell;
            this.totalCost = totalCost;
        }
    }

    @ParameterizedTest
    @MethodSource("gridPathfindingProvider")
    public void testGridPathfinding(TestCase test) {
        Visit<GridCell> visit = new BFS<>(test.problem);
        List<Move<GridCell>> path = visit.run(test.startCell);
        assertValidPath(test.problem, test.startCell, path);
        assertEquals(test.totalCost, path.size());
    }

    static Stream<TestCase> gridPathfindingProvider() {
        int[][] grid1 = {
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
        var problem1 = GridPathFindingProblem.newInstance(grid1, 9, 6);
        var start1 = GridCell.newInstance(problem1, 4, 2);
        var test1 = new TestCase(problem1, start1, 8);

        return Stream.of(test1);
    }
}
