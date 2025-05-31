package eth.epieffe.jwalker;

import java.util.List;

/**
 * Rappresenta una visita generica del grafo delle mosse di un Problem.
 */
public interface Visit<T> {

    /**
     * Esegue la visita dalla configurazione e ritorna un cammino fino ad una soluzione.
     */
    List<Move<T>> start(T config);
}
