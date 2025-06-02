package eth.epieffe.jwalker;

import java.util.function.Consumer;

/**
 * Un algoritmo generico che cerca la soluzione di un problema a partire
 * da una configurazione iniziale.
 */
public interface LocalSearch<T> {

    /**
     * Esegue la ricerca e ritorna la migliore soluzione trovata.
     * nb: NON è garantita la correttezza della soluzione!!!
     */
    default T run(T status) {
        return run(status, null);
    }

    T run(T status, Consumer<T> onVisit);
}
