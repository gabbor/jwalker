package epieffe.solver.algorithm;

import epieffe.solver.heuristic.Heuristic;
import epieffe.solver.problem.Problem;

/**
 * Un algoritmo generico che cerca la soluzione di un problema a partire
 * da una configurazione iniziale.
 * */
public interface Search {
	
	
    /**
     * Esegue la ricerca e ritorna la migliore soluzione trovata.
     * nb: NON è garantita la correttezza della soluzione!!!
     * */
	<T> T start(Problem<T> problem, T config, Heuristic<T> h);
}
