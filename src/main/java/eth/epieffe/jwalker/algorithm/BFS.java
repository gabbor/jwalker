package eth.epieffe.jwalker.algorithm;

import eth.epieffe.jwalker.Visit;
import eth.epieffe.jwalker.Move;
import eth.epieffe.jwalker.Problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class BFS<T> implements Visit<T> {

    private final Problem<T> problem;

    public BFS(Problem<T> problem) {
        Objects.requireNonNull(problem);
        this.problem = problem;
    }

    @Override
    public List<Move<T>> run(T config) {
        Move<T> sol = null;
        Set<T> visitedSet = new HashSet<>();
        Queue<Move<T>> frontierQueue = new LinkedList<>();
        Map<Move<T>, Move<T>> parentMap = new HashMap<>();
        Move<T> startMove = new Move<>("START", 0, config);
        parentMap.put(startMove, startMove);
        visitedSet.add(config);
        frontierQueue.add(startMove);
        boolean exitLoop = false;
        while (!frontierQueue.isEmpty() && !exitLoop) {
            Move<T> current = frontierQueue.poll();
            if (problem.isSolved(current.config)) {
                sol = current;
                exitLoop = true;
            } else {
                for (Move<T> move : problem.getMoves(current.config)) {
                    if (!visitedSet.contains(move.config)) {
                        visitedSet.add(move.config);
                        parentMap.put(move, current);
                        frontierQueue.add(move);
                    }
                }
            }
        }
        return buildPath(parentMap, sol);
    }

    private static <T> List<Move<T>> buildPath(Map<Move<T>, Move<T>> parentMap, Move<T> sol) {
        List<Move<T>> path = null;
        if (sol != null) {
            Stack<Move<T>> moveStack = new Stack<>();
            Move<T> child = sol;
            Move<T> parent = parentMap.get(child);
            while (parent != child) {
                moveStack.push(child);
                child = parent;
                parent = parentMap.get(child);
            }
            path = new ArrayList<>(moveStack.size());
            while (!moveStack.isEmpty()) {
                Move<T> move = moveStack.pop();
                path.add(move);
            }
        }
        return path;
    }
}
