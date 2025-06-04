package eth.epieffe.jwalker.algorithm;

import eth.epieffe.jwalker.Edge;
import eth.epieffe.jwalker.Graph;
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
        public final Graph<GridCell> graph;
        public final GridCell startCell;
        public final double totalCost;

        public TestCase(Graph<GridCell> graph, GridCell startCell, double totalCost) {
            this.graph = graph;
            this.startCell = startCell;
            this.totalCost = totalCost;
        }
    }

    @ParameterizedTest
    @MethodSource("gridPathfindingProvider")
    public void testGridPathfinding(TestCase test) {
        Visit<GridCell> visit = new BFS<>(test.graph);
        List<Edge<GridCell>> path = visit.run(test.startCell);
        assertValidPath(test.graph, test.startCell, path);
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
