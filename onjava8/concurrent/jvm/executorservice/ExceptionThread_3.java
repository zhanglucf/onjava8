package concurrent.jvm.executorservice;

import util.Print;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhangZhenhua
 * @date 2021/1/7 10:59
 */
public class ExceptionThread_3 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        try {
            executor.execute(() -> {
                Print.printlnTN("");
                throw new RuntimeException();
            });
        } catch (Exception e) {
            //还是抓不到异常
            e.printStackTrace();
        }

        executor.awaitTermination(3, TimeUnit.SECONDS);
        executor.shutdown();
    }
}
