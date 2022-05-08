package epieffe.solver.example;

import java.util.Arrays;

public class Main {

    private static final String USAGE = "USAGE: epieffe-solver npuzzle <path>" +
            "OR epieffe-solver nqueens <size> [max side moves]";

    public static void main(String... args) {
        if (args.length == 0) {
            System.out.println(USAGE);
            return;
        }
        switch (args[0]) {
            case "npuzzle":
                NPuzzleFromFileExample.main(args[1]);
                break;
            case "nqueens":
                String[] nqueensArgs = Arrays.copyOfRange(args, 1, args.length);
                NQueensExample.main(nqueensArgs);
                break;
            default:
                System.out.println(USAGE);
        }
    }
}
