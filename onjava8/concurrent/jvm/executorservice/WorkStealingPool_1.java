package concurrent.jvm.executorservice;

import concurrent.Nap;
import util.Print;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author ZhangZhenhua
 * @date 2021/1/7 10:46
 */
public class WorkStealingPool_1 {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newWorkStealingPool(3);

        Runnable task = ()->{
            new Nap(100);
            Print.printlnTN("");
        };

        IntStream.range(0, 10)
                .mapToObj(x -> task)
                .forEach(executor::submit);

        executor.awaitTermination(3, TimeUnit.SECONDS);
    }

}
