package designpattern;

// patterns/strategy/StrategyPattern2.java // {java patterns.strategy.StrategyPattern2}
import java.util.function.*;
import java.util.*;

// "Context" is now incorporated:
class FindMinima2 {
    Function<List<Double>, List<Double>> algorithm;
    FindMinima2() { leastSquares(); } // default
    // The various strategies:
    void leastSquares() {
        algorithm = (line) -> Arrays.asList(1.1, 2.2);
    }
    void perturbation() {
        algorithm = (line) -> Arrays.asList(3.3, 4.4);
    }
    void bisection() {
        algorithm = (line) -> Arrays.asList(5.5, 6.6);
    }
    List<Double> minima(List<Double> line) {
        return algorithm.apply(line);
    }
}

public class StrategyPattern2 {
    public static void main(String[] args) {
        FindMinima2 solver = new FindMinima2();
        List<Double> line = Arrays.asList();
        System.out.println(solver.minima(line));
        solver.bisection();
        System.out.println(solver.minima(line));
    }
}

