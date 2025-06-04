package eth.epieffe.jwalker.gridpathfinding;

import eth.epieffe.jwalker.Move;
import eth.epieffe.jwalker.Problem;

import java.util.ArrayList;
import java.util.List;

public class GridPathFindingProblem implements Problem<GridCell> {

    private final byte[][] grid;
    private final int targetRow;
    private final int targetCol;

    private GridPathFindingProblem(byte[][] grid, int targetRow, int targetCol) {
        this.grid = grid;
        this.targetRow = targetRow;
        this.targetCol = targetCol;
    }


    public static GridPathFindingProblem newInstance(int[][] grid, GridCell targetCell) {
        return newInstance(grid, targetCell.row, targetCell.col);
    }

    public static GridPathFindingProblem newInstance(int[][] grid, int targetRow, int targetCol) {
        if (grid.length == 0) {
            throw new IllegalArgumentException("Empty grid");
        }
        int width = grid.length;
        int height = grid[0].length;
        if (targetRow < 0 || targetCol < 0 || targetRow >= height || targetCol >= width) {
            throw new IllegalArgumentException("Target cell out of grid range");
        }
        if (grid[targetRow][targetCol] <= 0) {
            throw new IllegalArgumentException("Target cell is blocked");
        }

        byte[][] newGrid = new byte[width][height];
        for (int i = 0; i < width; ++i) {
            if (grid[i].length != height) {
                throw new IllegalArgumentException("Invalid grid");
            }
            for (int j = 0; j < height; ++j) {
                int cell = grid[i][j];
                if (cell > Byte.MAX_VALUE) {
                    throw new IllegalArgumentException("Cell value too high: " + cell);
                }
                newGrid[i][j] = (byte)(Math.max(cell, 0));
            }
        }
        return new GridPathFindingProblem(newGrid, targetRow, targetCol);
    }

    @Override
    public List<Move<GridCell>> getMoves(GridCell cell) {
        List<Move<GridCell>> moves = new ArrayList<>(8);
        int width = grid.length;
        int height = grid[0].length;

        int[][] directions = {
                {0, -1}, {0, 1}, {-1, 0}, {1, 0},
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

        String[] moveNames = {"LEFT", "RIGHT", "UP", "DOWN", "UP-LEFT", "UP-RIGHT", "DOWN-LEFT", "DOWN-RIGHT"};

        for (int i = 0; i < directions.length; i++) {
            int newRow = cell.row + directions[i][0];
            int newCol = cell.col + directions[i][1];

            if (newRow >= 0 && newRow < width && newCol >= 0 && newCol < height) {
                byte cost = grid[newRow][newCol];
                if (cost > 0) {
                    GridCell newCell = new GridCell(newRow, newCol);
                    moves.add(new Move<>(moveNames[i], cost, newCell));
                }
            }
        }

        return moves;
    }


    @Override
    public boolean isSolved(GridCell cell) {
        return cell.row == this.targetRow &&
                cell.col == this.targetCol;
    }

    public String prettyString(GridCell cell) {
        StringBuilder sb = new StringBuilder();
        sb.append("GridPathFindingProblem{\n");
        sb.append("  currentPosition=(").append(cell.row).append(", ").append(cell.col).append("),\n");
        sb.append("  targetPosition=(").append(targetRow).append(", ").append(targetCol).append("),\n");
        sb.append("  gridSize=(").append(grid.length).append("x").append(grid[0].length).append(")\n");
        sb.append("  Grid:\n");

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (i == cell.row && j == cell.col) {
                    sb.append("🌟 ");
                } else if (i == targetRow && j == targetCol) {
                    sb.append("🎯 ");
                } else if (grid[i][j] == 0) {
                    sb.append("⬛ ");
                } else {
                    sb.append("⬜ ");
                }
            }
            sb.append("\n");
        }

        sb.append("}");
        return sb.toString();
    }

    public int getCost(int row, int col) {
        return grid[row][col];
    }

    public int getGridWidth() {
        return this.grid[0].length;
    }

    public int getGridHeight() {
        return this.grid.length;
    }
}
