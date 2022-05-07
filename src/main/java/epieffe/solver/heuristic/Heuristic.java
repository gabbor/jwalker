package epieffe.solver.heuristic;

/**
 * Created by user on 03/03/17.
 */
public interface Heuristic<T> {
	
    /**Valuta quanto la configurazione del problema è vicina ad una soluzione.
     * più il valore di ritorno è piccolo più è vicino ad una soluzione.
     * se la configurazione del problema è già una soluzione ritorna 0 */
    int eval(T config);
}
