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

    public static GridPathFindingProblem newInstance(byte[][] grid) {
        int targetRow = -1, targetCol = -1;

        byte[][] newGrid = new byte[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                newGrid[i][j] = grid[i][j];
                if (grid[i][j] == 2) {
                    targetRow = i;
                    targetCol = j;
                    newGrid[i][j] = 1;
                }
            }
        }
        if (targetRow == -1) {
            throw new IllegalArgumentException("The grid does not contain a target position (2).");
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

            if (newRow >= 0 && newRow < width && newCol >= 0 && newCol < height && grid[newRow][newCol] == 1) {
                GridCell newCell = new GridCell(newRow, newCol);
                moves.add(new Move<>(moveNames[i], 1, newCell));
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

    public boolean getCell(int row, int col) {
        int cell = grid[row][col];
        return cell != 0;
    }

    public int getGridWidth() {
        return this.grid[0].length;
    }

    public int getGridHeight() {
        return this.grid.length;
    }
}
