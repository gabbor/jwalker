package epieffe.solver.algorithm;

import java.util.List;

import epieffe.solver.problem.Move;
import epieffe.solver.problem.Problem;

/**Rappresenta una visita generica del grafo delle mosse di un Problem
 */
public interface Visit {
    /**esegue la visita dalla configurazione p e ritorna un cammino da p ad una soluzione*/
    List<Move> start(Problem p);
}
