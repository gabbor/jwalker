package eth.epieffe.jwalker.algorithm;

import eth.epieffe.jwalker.Move;
import eth.epieffe.jwalker.Problem;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PathAssertions {

    public static <T> void assertValidPath(Problem<T> problem, T start, List<Move<T>> path) {
        assertNotNull(path);

        T current = start;
        for (Move<T> move : path) {
            boolean valid = problem.getMoves(current).contains(move);
            assertTrue(valid, "Invalid move");
            current = move.status;
        }
        assertTrue(problem.isSolved(current), "Invalid solution");
    }
}
