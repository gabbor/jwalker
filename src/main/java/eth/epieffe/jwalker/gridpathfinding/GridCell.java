package eth.epieffe.jwalker.gridpathfinding;

import java.util.Objects;

public class GridCell {

    public final int row;
    public final int col;

    public static GridCell newInstance(GridPathFindingProblem problem, int row, int col) {
        try {
            if (!problem.getCell(row, col)) {
                throw new IllegalArgumentException("Cell is blocked");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Cell not in grid");
        }
        return new GridCell(row, col);
    }

    GridCell(int r, int c) {
        this.row = r;
        this.col = c;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GridCell gridCell = (GridCell) o;
        return row == gridCell.row && col == gridCell.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "GridCell{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }

}