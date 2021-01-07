package concurrent.jvm.uncaughtex;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhangZhenhua
 * @date 2021/1/7 12:16
 */
public class SettingDefaultHandler_2 {
    /**
     *  设置默认的未捕获异常处理器
     *  只有在每个线程没有设置异常处理器时候，默认处理器才会被调用。
     *  系统会检查线程专有的版本，如果没有，则检查是否线程组中有专有的 uncaughtException() 方法；
     * 如果都没有，就会调用 defaultUncaughtExceptionHandler 方法。
     */
    public static void main(String[] args) throws InterruptedException {
        new SettingDefaultHandler_2().method();
    }

    Thread.UncaughtExceptionHandler ueh = (th, e) -> System.err.println("caught " + e);

    public void method() throws InterruptedException {
        Thread.setDefaultUncaughtExceptionHandler(ueh);
        ExecutorService executor = Executors.newCachedThreadPool();
        try {
            executor.execute(() -> {
                throw new RuntimeException(" 子线程抛出异常 ");
            });
        } catch (Exception e) {
            System.err.println(e.getClass());
        }
        executor.awaitTermination(3, TimeUnit.SECONDS);
        executor.shutdown();
    }

}
