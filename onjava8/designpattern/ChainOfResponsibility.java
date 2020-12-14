package designpattern;

// patterns/chain/ChainOfResponsibility.java
// Using the Functional interface
// {java patterns.chain.ChainOfResponsibility}
import java.util.*;
import java.util.function.*;

/**
 *  责任链模式：可以被看作一个使用了 策略对象的 “递归的动态一般化”
 *
 *   我们进行一次调用，责任链上的每个策略都试图满足这个调用，
 *   直到有一个策略满足调用，或者到达责任链的末尾才结束
 *
 *   一定含有一个递归方法，这是责任链模式中的核心
 *   责任链可以看作是一个更一般的动态构建的switch语句
 */
class Result {
    boolean success;
    List<Double> line;
    Result(List<Double> data) {
        success = true;
        line = data;
    }
    Result() {
        success = false;
        line = Collections.<Double>emptyList();
    }
}

class Fail extends Result {}

interface Algorithm {
    Result algorithm(List<Double> line);
}

class FindMinima_ {

    public static Result leastSquares(List<Double> line) {
        System.out.println("LeastSquares.algorithm");
        boolean weSucceed = false;
        if(weSucceed) // Actual test/calculation here
            return new Result(Arrays.asList(1.1, 2.2));
        else // Try the next one in the chain:
            return new Fail();
    }

    public static Result perturbation(List<Double> line) {
        System.out.println("Perturbation.algorithm");
        boolean weSucceed = false;
        if(weSucceed) // Actual test/calculation here
            return new Result(Arrays.asList(3.3, 4.4));
        else
            return new Fail();
    }

    public static Result bisection(List<Double> line) {
        System.out.println("Bisection.algorithm");
        boolean weSucceed = true;
        if(weSucceed) // Actual test/calculation here
            return new Result(Arrays.asList(5.5, 6.6));
        else
            return new Fail();
    }

    static List<Function<List<Double>, Result>> algorithms =  Arrays
            .asList(FindMinima_::leastSquares,FindMinima_::perturbation,FindMinima_::bisection);

    public static Result minima(List<Double> line) {
        for(Function<List<Double>, Result> alg : algorithms) {
            Result result = alg.apply(line);
            if(result.success){
                return result;
            }
        }
        return new Fail();
    }
}

public class ChainOfResponsibility {
    public static void main(String[] args) {
        FindMinima_ solver = new FindMinima_();
        List<Double> line = Arrays.asList(
                1.0, 2.0, 1.0, 2.0, -1.0,
                3.0, 4.0, 5.0, 4.0);
        Result result = solver.minima(line);
        if(result.success)
            System.out.println(result.line);
        else
            System.out.println("No algorithm found");
    }
}

