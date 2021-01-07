package concurrent.jvm.atomic;


import concurrent.jvm.TimedAbort;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;
/**
 * @author ZhangZhenhua
 * @date 2021/1/7 14:53
 */
public class EvenChecker_1 implements Runnable {
    private IntGenerator_0 generator;
    private final int id;
    public EvenChecker_1(IntGenerator_0 generator, int id) {
        this.generator = generator;
        this.id = id;
    }

    @Override
    public void run() {
        while(!generator.isCanceled()) {
            int val = generator.next();
            if(val % 2 != 0) {
                System.out.println(val + " not even!");
                generator.cancel(); // Cancels all EvenCheckers
            }
        }
    }

    // Test any IntGenerator_0:
    public static void test(IntGenerator_0 gp, int count) {
        List<CompletableFuture<Void>> checkers =
                IntStream.range(0, count)
                        .mapToObj(i -> new EvenChecker_1(gp, i))
                        .map(CompletableFuture::runAsync)
                        .collect(Collectors.toList());
        checkers.forEach(CompletableFuture::join);
    }

    // Default value for count:
    public static void test(IntGenerator_0 gp) {
        new TimedAbort(4, "No odd numbers discovered");
        test(gp, 10);
    }
}

