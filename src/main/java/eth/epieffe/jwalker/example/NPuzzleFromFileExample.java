package eth.epieffe.jwalker.example;

import eth.epieffe.jwalker.Visit;
import eth.epieffe.jwalker.Visits;
import eth.epieffe.jwalker.Move;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NPuzzleFromFileExample {

    public static void main(String... args) {
        solveNPuzzleFromFile(args[0]);
    }

    public static void solveNPuzzleFromFile(String path) {
        try {
            NPuzzle config = parseNPuzzleFromFile(path);
            System.out.println("Initial configuration:");
            System.out.println(config);
            System.out.println("--------------");
            Visit<NPuzzle> visit = Visits.greedyBestFirst(new NPuzzleProblem(), NPuzzleHeuristic::manhattanDistance);
            List<Move<NPuzzle>> moves = visit.run(config);
            System.out.println("Move list:");
            moves.forEach(s -> System.out.println(s.move));
        } catch (FileNotFoundException e) {
            System.err.println("Invalid file path");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static NPuzzle parseNPuzzleFromFile(String path) throws IOException {
        File file = new File(path);
        List<Byte> values = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        int size = 0;
        String st;
        while ((st = br.readLine()) != null) {
            size++;
            String[] line = st.split(",|;");
            for (String value : line) {
                values.add(Byte.valueOf(value.trim()));
            }
        }
        byte[][] table = new byte[size][size];
        for (int i = 0; i < values.size(); i++) {
            int row = i / size;
            int column = i % size;
            table[row][column] = values.get(i);
        }
        return NPuzzle.newInstance(table);
    }
}
