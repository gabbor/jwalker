package epieffe.solver.heuristic;

import epieffe.solver.problem.Problem;

/**
 * Created by user on 03/03/17.
 */
public interface Heuristic<T extends Problem> {
	
    /**Valuta quanto l'istanza p del problema è vicina ad una soluzione.
     * più il valore di ritorno è piccolo più è vicino ad una soluzione.
     * se l'istanza del problema è già una soluzione ritorna 0
     * @throws ClassCastException se il problema non è del tipo giusto*/
    int eval(T problem);
}
