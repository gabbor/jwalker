package epieffe.solver.problem;

import java.util.List;

/**
 * Rappresenta un'istanza di un problema generico
 */
public interface Problem<T> {
    
    /**ritorna tutte le configurazioni del problema ottenibili
     * con una mossa a partire dalla configurazione in input*/
    List<Move<T>> getMoves(T config);

    /**Ritorna true se l'istanza del problema è una soluzione*/
    boolean isSolved(T config);
}
