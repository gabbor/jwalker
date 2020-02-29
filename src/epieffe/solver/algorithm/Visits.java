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

    static <T extends Problem>  List<Move> bestFirstGreedy(T p, Heuristic<T> h) {
        return bestFirst(p, h, 1, true);
    }

    static <T extends Problem> List<Move> aStar(T p, Heuristic<T> h) {
        return aStar(p, h, 1);
    }

    static <T extends Problem>  List<Move> aStar(T p, Heuristic<T> h, int approx) {
        return bestFirst(p, h, approx, false);
    }

    static List<Move> dijkstra(Problem p) {
        return bestFirst(p, null, 1, false);
    }

    static List<Move> BFS(Problem p) {
        Move sol = null;
        Set<Problem> visitedSet = new HashSet<>();
        Queue<Move> frontierQueue = new LinkedList<>();
        Map<Move, Move> parentMap = new HashMap<>();
        Move startMove = new Move("START", 0, p);
        parentMap.put(startMove, startMove);
        visitedSet.add(p);
        frontierQueue.add(startMove);
        boolean exitLoop = false;
        while (!frontierQueue.isEmpty() && !exitLoop) {
            Move current = frontierQueue.poll();
            if (current.config.isSolved()) {
                sol = current;
                exitLoop = true;
            } else {
                for (Move move : current.config.getMoves()) {
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
    private static <T extends Problem> List<Move> bestFirst(T p, Heuristic<T> h, int hmul, boolean isGreedy) {
        if (h == null) {
            if (isGreedy) {
                throw new IllegalArgumentException("can't be greedy without heuristic");
            }
            h = pr -> 0;
        }
        Move sol = null;
        MinHeap<Move> frontier = new MinHeap<>();
        Map<Move, Move> parentMap = new HashMap<>();
        Move startMove = new Move("START", 0, p);
        parentMap.put(startMove, startMove);
        Map<Problem, Integer> distMap = new HashMap<>();
        distMap.put(p, 0);
        frontier.insertOrUpdate(startMove, h.eval(p) * hmul);
        while (!frontier.isEmpty() && sol == null) {
            Move current = frontier.extractMin();
            if (current.config.isSolved()) {
                sol = current;
            } else {
                int currentDist = distMap.get(current.config);
                for ( Move move : current.config.getMoves() ) {
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

    private static List<Move> buildPath(Map<Move, Move> parentMap, Move sol) {
        List<Move> path = null;
        if (sol != null) {
            Stack<Move> moveStack = new Stack<>();
            Move child = sol;
            Move parent = parentMap.get(child);
            while (parent != child) {
                moveStack.push(child);
                child = parent;
                parent = parentMap.get(child);
            }
            path = new ArrayList<>(moveStack.size());
            while (!moveStack.isEmpty()) {
                Move move = moveStack.pop();
                path.add(move);
            }
        }
        return path;
    }
}
