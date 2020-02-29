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
			public <T extends Problem> T start(T p, Heuristic<T> h) {
				return Searches.steepestDescent(p, h, max);
			}		
    	};
    }
    
}
