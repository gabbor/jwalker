package epieffe.solver.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import epieffe.solver.heuristic.Heuristic;
import epieffe.solver.problem.Move;
import epieffe.solver.problem.Problem;
import epieffe.solver.util.MinHeap;

/**
 * Created by user on 04/03/17.
 */
class Visits {

    static <T>  List<Move<T>> bestFirstGreedy(Problem<T> problem, T config, Heuristic<T> h) {
        return bestFirst(problem, config, h, 1, true);
    }

    static <T> List<Move<T>> aStar(Problem<T> problem, T config, Heuristic<T> h) {
        return aStar(problem, config, h, 1);
    }

    static <T>  List<Move<T>> aStar(Problem<T> problem, T config, Heuristic<T> h, int approx) {
        return bestFirst(problem, config, h, approx, false);
    }

    static <T> List<Move<T>> dijkstra(Problem<T> problem, T config) {
        return bestFirst(problem, config, null, 1, false);
    }

    
    static <T> List<Move<T>> BFS(Problem<T> problem, T config) {
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
            if ( problem.isSolved(current.config) ) {
                sol = current;
                exitLoop = true;
            } else {
                for (Move<T> move : problem.getMoves(current.config) ) {
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

    
    /**Ricerca best first.
     * @param p Problem su cui eseguire la ricerca
     * @param h euristica del problema, può essere null
     * @param hmul costante per cui verranno moltiplicati i valori di h
     * @param isGreedy se è false considera anche il percorso fatto finora nell'espansione della frontiera*/
    private static <T> List<Move<T>> bestFirst(
    		Problem<T> problem,
    		T config,
    		Heuristic<T> h,
    		int hmul,
    		boolean isGreedy)
    {
        if (h == null) {
            if (isGreedy) {
                throw new IllegalArgumentException("can't be greedy without heuristic");
            }
            h = pr -> 0;
        }
        Move<T> sol = null;
        MinHeap<Move<T>> frontier = new MinHeap<>();
        Map<Move<T>, Move<T>> parentMap = new HashMap<>();
        Move<T> startMove = new Move<>("START", 0, config);
        parentMap.put(startMove, startMove);
        Map<T, Integer> distMap = new HashMap<>();
        distMap.put(config, 0);
        frontier.insertOrUpdate(startMove, h.eval(config) * hmul);
        while (!frontier.isEmpty() && sol == null) {
            Move<T> current = frontier.extractMin();
            if ( problem.isSolved(current.config) ) {
                sol = current;
            } else {
                int currentDist = distMap.get(current.config);
                for ( Move<T> move : problem.getMoves(current.config) ) {
                    Integer dist = distMap.get(move.config);
                    int newDist = currentDist + move.cost;
                    if (dist == null || dist > newDist) {
                        distMap.put(move.config, newDist);
                        parentMap.put(move, current);
                        int priority = h.eval((T)move.config) * hmul;
                        if (!isGreedy) {
                            priority += newDist;
                        }
                        frontier.insertOrUpdate(move, priority);
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
