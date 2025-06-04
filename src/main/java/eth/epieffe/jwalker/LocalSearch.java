package eth.epieffe.jwalker;

import java.util.function.Consumer;

/**
 * Un algoritmo generico che cerca la soluzione di un problema a partire
 * da una configurazione iniziale.
 */
public interface LocalSearch<N> {

    /**
     * Esegue la ricerca e ritorna la migliore soluzione trovata.
     * nb: NON è garantita la correttezza della soluzione!!!
     */
    default N run(N node) {
        return run(node, null);
    }

    N run(N node, Consumer<N> onVisit);
}
