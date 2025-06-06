package eth.epieffe.jwalker.algorithm;

import eth.epieffe.jwalker.Edge;
import eth.epieffe.jwalker.Graph;
import eth.epieffe.jwalker.LocalSearch;
import eth.epieffe.jwalker.Heuristic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;

public class SteepestDescent<N> implements LocalSearch<N> {

    private final Random random = new Random();

    private final Graph<N> graph;

    private final Heuristic<N> heuristic;

    private final int maxSides;

    public SteepestDescent(Graph<N> graph, Heuristic<N> heuristic, int maxSides) {
        if (maxSides < 0) {
            throw new IllegalArgumentException("Argument maxSides must not be negative");
        }
        this.graph = Objects.requireNonNull(graph);
        this.heuristic = Objects.requireNonNull(heuristic);
        this.maxSides = maxSides;
    }

    @Override
    public N run(N node, Consumer<N> onVisit) {
        N sol = null;
        N localBest = node;
        double localBestH = heuristic.eval(node);
        int countSides = 0;
        while (sol == null) {
            if (onVisit != null) {
                onVisit.accept(localBest);
            }
            double oldBestH = localBestH;
            List<Edge<N>> edges = graph.outgoingEdges(localBest);
            List<N> bestMoveList = new ArrayList<>();
            for (Edge<N> edge : edges) {
                double newH = heuristic.eval(edge.destination);
                if (newH <= localBestH) {
                    if (newH < localBestH) {
                        localBestH = newH;
                        bestMoveList.clear();
                    }
                    bestMoveList.add(edge.destination);
                }
            }
            if (!bestMoveList.isEmpty()) {
                int randomIndex = random.nextInt(bestMoveList.size());
                localBest = bestMoveList.get(randomIndex);
                if (localBestH == oldBestH) {
                    countSides++;
                    if (countSides >= maxSides) {
                        sol = localBest;
                    }
                }
            } else {
                sol = localBest;
            }
        }
        return sol;
    }
}
