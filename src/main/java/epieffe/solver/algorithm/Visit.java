package epieffe.solver.algorithm;

import java.util.List;

import epieffe.solver.heuristic.Heuristic;
import epieffe.solver.problem.Move;
import epieffe.solver.problem.Problem;

/**Rappresenta una visita generica del grafo delle mosse di un Problem
 */
public interface Visit {
	
    /**esegue la visita dalla configurazione e ritorna un cammino fino ad una soluzione*/
    <T> List<Move<T>> start(Problem<T> problem, T config, Heuristic<T> h);
}
