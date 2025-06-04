package eth.epieffe.jwalker.npuzzle;

import eth.epieffe.jwalker.Visit;
import eth.epieffe.jwalker.Visits;
import eth.epieffe.jwalker.Edge;

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
            NPuzzle status = parseNPuzzleFromFile(path);
            System.out.println("Initial status:");
            System.out.println(status);
            System.out.println("--------------");
            Visit<NPuzzle> visit = Visits.greedyBestFirst(new NPuzzleGraph(), NPuzzleHeuristic::manhattanDistance);
            List<Edge<NPuzzle>> edges = visit.run(status);
            System.out.println("Move list:");
            edges.forEach(s -> System.out.println(s.label));
        } catch (FileNotFoundException e) {
            System.err.println("Invalid file path");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static NPuzzle parseNPuzzleFromFile(String path) throws IOException {
        File file = new File(path);
        List<Integer> values = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null) {
            String[] line = st.split(",|;");
            for (String value : line) {
                values.add(Integer.valueOf(value.trim()));
            }
        }
        return NPuzzle.newInstance(values.stream().mapToInt(v -> v).toArray());
    }
}
