package eth.epieffe.jwalker;

public class Move<T> {

    public final String move;

    public final int cost;

    public final T config;

    public Move(String move, int cost, T config) {
        this.move = move;
        this.cost = cost;
        this.config = config;
    }
}
