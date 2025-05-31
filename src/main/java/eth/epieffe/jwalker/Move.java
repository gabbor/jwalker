package eth.epieffe.jwalker;

import java.util.Objects;

public class Move<T> {

    public final String move;

    public final int cost;

    public final T config;

    public Move(String move, int cost, T config) {
        this.move = move;
        this.cost = cost;
        this.config = config;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;
        Move<?> move1 = (Move<?>) o;
        return cost == move1.cost && Objects.equals(move, move1.move) && Objects.equals(config, move1.config);
    }

    @Override
    public int hashCode() {
        return Objects.hash(move, cost, config);
    }
}
