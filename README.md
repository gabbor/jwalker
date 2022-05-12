# Java Solver
Strongly typed Java library that solves user defined problems with various graph search algorithms.

As an example, it can solve *NPuzzle* ([Wikipedia](https://en.wikipedia.org/wiki/15_puzzle)) and *NQueens* ([Wikipedia](https://en.wikipedia.org/wiki/Eight_queens_puzzle)) out of the box.

## Overview
To define your custom problem you must implement the [Problem](src/main/java/epieffe/solver/problem/Problem.java) interface. For example implementations see [NPuzzleProblem](src/main/java/epieffe/solver/problem/NPuzzleProblem.java) or [NQueensProblem](src/main/java/epieffe/solver/problem/NQueensProblem.java) classes.

You also need to define a heuristic for that problem implementing the [Heuristic](src/main/java/epieffe/solver/heuristic/Heuristic.java) interface. For example implementations see [NPuzzleHeuristic](src/main/java/epieffe/solver/heuristic/NPuzzleHeuristic.java) or [NQueensHeuristic](src/main/java/epieffe/solver/heuristic/NQueensHeuristic.java) classes.

Then you can try to find a solution for your problem using a built-in solver that can be instantiated using [VisitFactory](src/main/java/epieffe/solver/algorithm/VisitFactory.java) or [SearchFactory](src/main/java/epieffe/solver/algorithm/SearchFactory.java) classes.

## Built-in algorithms
Here we describe the built-in search algorithms. Developers may add new algorithms by implementing the relative Java interface.

### Visits
A Visit algorithm starts from an initial (user defined) problem configuration and explores the move graph to find the shortest path possible that brings from the initial configuration to a correct solution. Some visits are guaranteed to find an optimal path, while others sacrifice optimality for efficiency.

Implements the [Visit](src/main/java/epieffe/solver/algorithm/Visit.java) interface.

#### built-in visits:
- A* ([Wikipedia](https://en.wikipedia.org/wiki/A*_search_algorithm))
- Best First ([Wikipedia](https://en.wikipedia.org/wiki/Best-first_search))
- BFS ([Wikipedia](https://en.wikipedia.org/wiki/Breadth-first_search))
- Dijkstra ([Wikipedia](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm))

### Searches
A Search algorithm starts from an initial problem configuration (usually randomly picked) and iteratively apply moves in order to get close to a solution. Only the found solution is returned, while the path that brings from the initial configuration to the solution is not of interest when using this kind of algorithms.

**The correctness of the found solution is not guaranteed**. If the search does not find a correct solution it returns a configuration that is as much close as possible to correctness.

Implements the [Search](src/main/java/epieffe/solver/algorithm/Search.java) interface.
#### built-in searches:
- Steepest Descent ([Wikipedia](https://en.wikipedia.org/wiki/Gradient_descent))

## Quick demo
The [Main](src/main/java/epieffe/solver/example/Main.java) class can be used to quickly solve *NPuzzle* or *NQueens* problems.

You can quickly build an executable jar file using [Maven](https://maven.apache.org/).
```bash
mvn clean package
```

Then you'll find your executable jar file at `target/epieffe-solver.jar`.

### NPuzzle demo
The executable jar file can easily solve *NPuzzle* problem instances using the *Best First* visit.

You need to pass an initial problem configuration to the executable jar file as a csv file path command line argument.

Some example *NPuzzle* initial configurations can be found in the `examples` folder.

```bash
java -jar target/epieffe-solver.jar npuzzle examples/npuzzle1.csv
```

### NQueens demo
The jar file can also solve *NQueens* problem instances using the *Steepest Descent* search.

You need to pass the number of queens in the chess board as a command line argument.

```bash
java -jar target/epieffe-solver.jar nqueens 8
```

## Import as dependency in your project
Obviously you can add the `epieffe-solver.jar` to the classpath. Then you can define your custom [Problem](src/main/java/epieffe/solver/problem/Problem.java) and [Heuristic](src/main/java/epieffe/solver/heuristic/Heuristic.java) and try to feed the built-in searches and visits with your custom problem.

If you are using [Maven](https://maven.apache.org/) in your project you can easily add this library to the classpath as a Maven dependency.

First install this library in your local Maven repository:
```bash
mvn clean install
```

Then you can add this dependency in your project's `pom.xml` file.
```xml
<dependency>
    <groupId>epieffe</groupId>
    <artifactId>epieffe-solver</artifactId>
    <version>1.0.0</version>
</dependency>
```
