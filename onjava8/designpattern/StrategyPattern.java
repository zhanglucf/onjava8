package designpattern;

// patterns/strategy/StrategyPattern.java
// {java patterns.strategy.StrategyPattern}
import java.util.function.*;
import java.util.*;

// The common strategy base type:
class FindMinima {
    Function<List<Double>, List<Double>> algorithm;
}

// The various strategies:
class LeastSquares extends FindMinima {
    LeastSquares() {
        // Line is a sequence of points (Dummy data):
        algorithm = (line) -> Arrays.asList(1.1, 2.2);
    }
}

class Perturbation extends FindMinima {
    Perturbation() {
        algorithm = (line) -> Arrays.asList(3.3, 4.4);
    }
}

class Bisection extends FindMinima {
    Bisection() {
        algorithm = (line) -> Arrays.asList(5.5, 6.6);
    }
}

// The "Context" controls the strategy:
class MinimaSolver {
    private FindMinima strategy;
    MinimaSolver(FindMinima strat) {
        strategy = strat;
    }
    List<Double> minima(List<Double> line) {
        return strategy.algorithm.apply(line);
    }
    void changeAlgorithm(FindMinima newAlgorithm) {
        strategy = newAlgorithm;
    }
}

public class StrategyPattern {
    public static void main(String[] args) {
        MinimaSolver solver =
                new MinimaSolver(new LeastSquares());
        List<Double> line = Arrays.asList();
        System.out.println(solver.minima(line));
        solver.changeAlgorithm(new Bisection());
        System.out.println(solver.minima(line));
    }
}

