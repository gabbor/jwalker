package epieffe.solver.algorithm;

import epieffe.solver.heuristic.Heuristic;
import epieffe.solver.problem.Problem;

/**
 * Created by user on 07/03/17.
 */
public class SearchFactory {
	
    public static  Search steepestDescent(int max) {
    	return new Search() {

			@Override
			public <T> T start(Problem<T> problem, T config, Heuristic<T> h) {
				return Searches.steepestDescent(problem, config, h, max);
			}		
    	};
    }
    
}
