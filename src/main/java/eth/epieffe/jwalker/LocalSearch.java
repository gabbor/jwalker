package eth.epieffe.jwalker;

/**
 * Un algoritmo generico che cerca la soluzione di un problema a partire
 * da una configurazione iniziale.
 */
public interface LocalSearch<T> {

    /**
     * Esegue la ricerca e ritorna la migliore soluzione trovata.
     * nb: NON è garantita la correttezza della soluzione!!!
     */
    T run(T config);
}
