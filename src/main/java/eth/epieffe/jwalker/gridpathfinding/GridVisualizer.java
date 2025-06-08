package eth.epieffe.jwalker.gridpathfinding;

import eth.epieffe.jwalker.Move;
import eth.epieffe.jwalker.Visit;
import eth.epieffe.jwalker.Visits;
import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GridVisualizer extends Application {
    private static final int GRID_SIZE = 600;
    private GridPane gridPane;
    private Rectangle[][] cells;
    private GridCell startCell, targetCell;
    TextField rowsField, colsField;
    private Button setPointsButton, runButton, resetButton;
    private final Timeline timeline = new Timeline();
    Set<FillTransition> transitions = new HashSet<>();
    private boolean gridConfirmed = false;
    ToggleButton algorithmToggle;

    @Override
    public void start(Stage stage) {
        VBox controlPanel = createControlPanel();
        gridPane = createEmptyGrid();
        // create horizontal layout
        HBox mainLayout = new HBox();
        mainLayout.getChildren().addAll(controlPanel, gridPane);
        // create main scene
        Scene scene = new Scene(mainLayout, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Grid Path Visualizer");
        stage.show();
    }

    private VBox createControlPanel() {
        VBox controlPanel = new VBox(10);
        controlPanel.setAlignment(Pos.CENTER);
        controlPanel.setPrefWidth(200);
        controlPanel.setStyle("-fx-background-color: #333; -fx-padding: 20px;");

        // text fields for setting a new grid size
        Label rowsLabel = new Label("Rows:");
        rowsLabel.setTextFill(Color.WHITE);
        rowsField = new TextField("50");
        rowsField.setOnAction(e -> createEmptyGrid());

        Label colsLabel = new Label("Columns:");
        colsLabel.setTextFill(Color.WHITE);
        colsField = new TextField("50");
        colsField.setOnAction(e -> createEmptyGrid());

        Label speedLabel = new Label("Speed:");
        speedLabel.setTextFill(Color.WHITE);

        Slider speedSlider = new Slider(0.1, 6, 1.0);
        speedSlider.setShowTickMarks(true);
        speedSlider.setShowTickLabels(true);
        speedSlider.setMajorTickUnit(1);
        speedSlider.setBlockIncrement(0.1);
        speedSlider.setShowTickMarks(false);
        speedSlider.setShowTickLabels(false);

        speedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            timeline.setRate(newValue.doubleValue());
        });

        Button clearGridButton = new Button("Clear Grid");
        clearGridButton.setOnAction(e -> createEmptyGrid());

        Button randomGridButton = new Button("Random Grid");
        randomGridButton.setOnAction(e -> {
            setGridConfirmedState(false);
            int blackCellPercentage = 30;
            generateRandomMap(blackCellPercentage);
        });

        setPointsButton = new Button("Set Points");
        setPointsButton.setOnAction(e -> setGridConfirmedState(true));

        runButton = new Button("Run");
        runButton.setOnAction(e -> {
            setRunState();
            runAlgorithm();
        });

        resetButton = new Button("Reset");
        resetButton.setOnAction(e -> {
            setGridConfirmedState(true);
            clearGrid();
        });

        algorithmToggle = new ToggleButton("Use A*");
        algorithmToggle.setOnAction(e -> {
            if (algorithmToggle.isSelected()) {
                algorithmToggle.setText("Use Dijkstra");
            } else {
                algorithmToggle.setText("Use A*");
            }
        });

        controlPanel.getChildren().addAll(
                algorithmToggle,
                rowsLabel, rowsField, colsLabel, colsField,
                speedLabel, speedSlider,
                clearGridButton, randomGridButton, setPointsButton,
                runButton, resetButton);

        return controlPanel;
    }

    private GridPane createEmptyGrid() {
        setInitialState();
        if (gridPane == null) {
            gridPane = new GridPane();
            gridPane.setPrefSize(GRID_SIZE, GRID_SIZE);
            gridPane.setStyle("-fx-background-color: #444;");
        }
        int rows = Integer.parseInt(rowsField.getText());
        int cols = Integer.parseInt(colsField.getText());
        initGrid(rows, cols, new HashSet<>());
        return gridPane;
    }

    private void initGrid(int rows, int cols, Set<String> blackCells) {
        gridPane.getChildren().clear();
        cells = new Rectangle[rows][cols];
        int cellSize = Math.max(10, GRID_SIZE / Math.max(rows, cols));
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Rectangle cell = new Rectangle(cellSize, cellSize, Color.LIGHTGRAY);
                cell.setArcWidth(8);
                cell.setArcHeight(8);
                cell.setEffect(new DropShadow(5, Color.BLACK));
                if (blackCells.contains(row + "," + col)) {
                    cell.setFill(Color.BLACK);
                }
                int finalRow = row, finalCol = col;
                cell.setOnMouseClicked(event -> handleCellClick(finalRow, finalCol));
                cells[row][col] = cell;
                gridPane.add(cell, col, row);
            }
        }
    }

    private void generateRandomMap(int blackCellPercentage) {
        int rows = Integer.parseInt(rowsField.getText());
        int cols = Integer.parseInt(colsField.getText());

        int totalCells = rows * cols;
        int blackCellsCount = totalCells * blackCellPercentage / 100;

        List<int[]> positions = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                positions.add(new int[]{i, j});
            }
        }
        Collections.shuffle(positions);

        Set<String> blackCells = new HashSet<>();
        for (int i = 0; i < blackCellsCount; i++) {
            int[] pos = positions.get(i);
            blackCells.add(pos[0] + "," + pos[1]);
        }

        initGrid(rows, cols, blackCells);
    }

    private void handleCellClick(int row, int col) {
        if (!gridConfirmed) {
            cells[row][col].setFill(cells[row][col].getFill() == Color.BLACK ? Color.LIGHTGRAY : Color.BLACK);
        } else if (startCell == null) {
            startCell = new GridCell(row, col);
            cells[row][col].setFill(Color.RED);
        } else if (targetCell == null) {
            targetCell = new GridCell(row, col);
            cells[row][col].setFill(Color.BLUE);
            runButton.setDisable(false);
        }
    }

    private void runAlgorithm() {
        if (startCell == null || targetCell == null) return;
        int[][] grid = new int[cells.length][cells[0].length];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                grid[i][j] = cells[i][j].getFill() == Color.BLACK ? 0 : 1;
            }
        }
        GridPathFindingProblem problem = GridPathFindingProblem.newInstance(grid, targetCell);
        Visit<GridCell> visit;
        if (algorithmToggle.isSelected()) {
            visit = Visits.dijkstra(problem);
        } else {
            visit = Visits.aStar(problem,
                    status -> Math.abs(targetCell.col - status.col) + Math.abs(targetCell.row - status.row));
        }
        List<GridCell> visitedCells = new ArrayList<>();
        List<Move<GridCell>> moves = visit.run(startCell, visitedCells::add);
        animatePath(visitedCells, moves);
    }


    private void animatePath(List<GridCell> visitedCells, List<Move<GridCell>> moves) {
        transitions.clear();
        for (int i = 0; i < visitedCells.size(); i++) {
            GridCell cell = visitedCells.get(i);

            if (cell.row == startCell.row && cell.col == startCell.col) continue;
            if (cell.row == targetCell.row && cell.col == targetCell.col) continue;

            KeyFrame frame = new KeyFrame(Duration.millis(5 * (i + 1)), e -> {
                FillTransition ft = new FillTransition(Duration.millis(5), cells[cell.row][cell.col], Color.LIGHTGRAY, Color.GREY);
                transitions.add(ft);
                ft.play();
            });
            timeline.getKeyFrames().add(frame);
        }
        timeline.setOnFinished(e -> {
            PauseTransition pause = new PauseTransition(Duration.millis(50));
            pause.setOnFinished(event -> highlightPath(moves));
            pause.play();
        });

        timeline.play();
    }

    private void highlightPath(List<Move<GridCell>> moves) {
        cells[startCell.row][startCell.col].setFill(Color.YELLOW);
        for (Move<GridCell> move : moves) {
            GridCell cell = move.status;
            cells[cell.row][cell.col].setFill(Color.YELLOW);
        }
    }

    private void clearGrid() {
        for (Rectangle[] cell : cells) {
            for (Rectangle rectangle : cell) {
                if (rectangle.getFill() == Color.GREY || rectangle.getFill() == Color.YELLOW ||
                        rectangle.getFill() == Color.RED || rectangle.getFill() == Color.BLUE) {
                    rectangle.setFill(Color.LIGHTGRAY);
                }
            }
        }
    }

    private void stopTimeline() {
        timeline.stop();
        timeline.getKeyFrames().clear();
        timeline.setOnFinished(null);
        for (FillTransition ft : transitions) {
            ft.stop();
            ft.getShape().setFill(ft.getFromValue());
        }
        transitions.clear();
    }

    private void setInitialState() {
        stopTimeline();
        gridConfirmed = false;
        startCell = null;
        targetCell = null;
        runButton.setDisable(true);
        resetButton.setDisable(true);
        setPointsButton.setDisable(false);
    }

    private void setGridConfirmedState(boolean confirmed) {
        stopTimeline();
        gridConfirmed = confirmed;
        startCell = null;
        targetCell = null;
        runButton.setDisable(true);
        resetButton.setDisable(true);
        setPointsButton.setDisable(confirmed);
    }

    private void setRunState() {
        if (!gridConfirmed) {
            throw new IllegalStateException("Grid is not confirmed!");
        }
        if (startCell == null || targetCell == null) {
            throw new IllegalStateException("Start and Target must be set!");
        }
        runButton.setDisable(true);
        resetButton.setDisable(false);
        setPointsButton.setDisable(true);
    }


    public static void main(String[] args) {
        launch();
    }
}
