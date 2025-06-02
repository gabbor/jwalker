package eth.epieffe.jwalker;

import java.util.Objects;

public class Move<T> {

    public final String move;

    public final double cost;

    public final T status;

    public Move(String move, double cost, T status) {
        this.move = move;
        this.cost = cost;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;
        Move<?> move1 = (Move<?>) o;
        return cost == move1.cost && Objects.equals(move, move1.move) && Objects.equals(status, move1.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(move, cost, status);
    }
}
