package eth.epieffe.jwalker;

import java.util.List;
import java.util.function.Consumer;

/**
 * Rappresenta una visita generica del grafo delle mosse di un Problem.
 */
public interface Visit<T> {

    /**
     * Esegue la visita dalla configurazione e ritorna un cammino fino ad una soluzione.
     */
    default List<Move<T>> run(T config) {
        return run(config, null);
    }

    List<Move<T>> run(T config, Consumer<T> onVisit);
}
