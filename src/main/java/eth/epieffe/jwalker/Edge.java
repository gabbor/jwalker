package eth.epieffe.jwalker;

import java.util.Objects;

public class Edge<T> {

    public final String label;

    public final double weight;

    public final T destination;

    public Edge(String label, double weight, T destination) {
        this.label = label;
        this.weight = weight;
        this.destination = destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge<?> other = (Edge<?>) o;
        return weight == other.weight &&
                Objects.equals(label, other.label) &&
                Objects.equals(destination, other.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, weight, destination);
    }
}
