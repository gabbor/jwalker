package epieffe.solver.algorithm;

import epieffe.solver.heuristic.Heuristic;

/**
 * Created by user on 05/03/17.
 */
public class VisitFactory {

    public static Visit bfs() {
        return (p) -> Visits.BFS(p);
    }

    public static Visit dijkstra() {
        return (p) -> Visits.dijkstra(p);
    }

    public static Visit aStar(Heuristic h) {
        return (p) -> Visits.aStar(p, h);
    }

    public static Visit aStar(Heuristic h, int approx) {
        return (p) -> Visits.aStar(p, h, approx);
    }

    public static Visit bestFirst(Heuristic h) {
        return (p) -> Visits.bestFirstGreedy(p, h);
    }
}
