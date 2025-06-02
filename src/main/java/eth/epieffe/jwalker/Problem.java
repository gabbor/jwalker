package eth.epieffe.jwalker;

import java.util.List;

/**
 * Rappresenta un'istanza di un problema generico.
 */
public interface Problem<T> {

    /**
     * Ritorna tutte le configurazioni del problema ottenibili
     * con una mossa a partire dalla configurazione in input.
     */
    List<Move<T>> getMoves(T status);

    /**
     * Ritorna true se l'istanza del problema è una soluzione.
     */
    boolean isSolved(T status);
}
