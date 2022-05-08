package epieffe.solver.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by user on 05/03/17.
 */
public class MinHeap<T> {

    private class Pair {
        final int priority;
        final T value;
        Pair(T v, int p) {
            priority = p;
            value = v;
        }
    }

    private Map<T, Pair> map = new HashMap<>();
    private Queue<Pair> pQueue = new PriorityQueue<>(
            Comparator.comparingInt((Pair o) -> o.priority)
    );

    /**
     * Inserisce il valore v nella coda con priorità p.
     * se v era già presente nella coda aggiorna la sua priorità.
     * */
    public void insertOrUpdate(T v, int p) {
        if ( map.containsKey(v) ) {
            Pair oldPair = map.get(v);
            boolean error = !pQueue.remove(oldPair);
            if (error) {
                System.err.println("if you see this probabily something is going wrong");
            }
        }
        Pair pair = new Pair(v, p);
        map.put(v, pair);
        pQueue.add(pair);
    }

    /**
     * Ritorna l'elemento con priorità minima senza rimuoverlo.
     * */
    public T minimum() {
        return pQueue.peek().value;
    }

    /**
     * Ritorna e rimuove l'elemento con priorità minima.
     * */
    public T extractMin() {
        return pQueue.poll().value;
    }

    /**
     * Cambia la priorità di v al valore p, se p è >= di quella attuale.
     * @return true se p >= della vecchia priorità di v.
     * */
    public boolean decreaseKey(T v, int p) {
        Pair oldPair = map.get(v);
        if (oldPair == null || p < oldPair.priority ) {
            return false;
        }
        insertOrUpdate(v, p);
        return true;
    }

    public boolean isEmpty() {
        return pQueue.isEmpty();
    }
}
