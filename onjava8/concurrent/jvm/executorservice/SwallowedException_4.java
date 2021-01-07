package concurrent.jvm.executorservice;

import util.Print;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhangZhenhua
 * @date 2021/1/7 10:59
 */
public class SwallowedException_4 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        /*
        在线程中抛出异常是很棘手的，需要特别注意的事情。
                executor.submit()不会抛出异常
                executor.execute()会抛出异常
         */

        executor.submit(()->{
//        executor.execute(()->{
            Print.printlnTN("");
            throw new RuntimeException();
        });

        executor.awaitTermination(3, TimeUnit.SECONDS);
        executor.shutdown();
    }
}
