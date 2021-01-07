package concurrent.jvm.uncaughtex;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhangZhenhua
 * @date 2021/1/7 11:56
 */
public class CaptureUncaughtException_1 {

    public static void main(String[] args) throws InterruptedException {
        new CaptureUncaughtException_1().method();
    }

    public void method() throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool(tf);
        try {
            executor.execute(()->{
                throw new RuntimeException(" ### ");
            });
        } catch (Exception e) {
            System.err.println(e.getClass());
        }
        executor.awaitTermination(3, TimeUnit.SECONDS);
        executor.shutdown();
    }

    Thread.UncaughtExceptionHandler ueh = (th, e) -> System.err.println("caught " + e);

    ThreadFactory tf = r -> {
        System.out.println(this + " creating new Thread");
        Thread t = new Thread(r);
        System.out.println("created " + t);
        t.setUncaughtExceptionHandler(ueh);
        System.out.println("eh = " + t.getUncaughtExceptionHandler());
        return t;
    };


}

