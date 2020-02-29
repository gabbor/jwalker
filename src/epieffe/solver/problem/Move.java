package epieffe.solver.problem;

public class Move {
	
    public final String move;
    
    public final int cost;
    
    public final Problem config;

    
    public Move(String move, int cost, Problem config) {
        this.move = move;
        this.cost = cost;
        this.config = config;
    }
	
	
}
