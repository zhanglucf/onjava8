package concurrent.jvm.executorservice;

import concurrent.Nap;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ZhangZhenhua
 * @date 2021/1/6 15:53
 */

public class ThreadSize_2 {
    static class Dummy extends Thread {
        @Override
        public void run() {
            new Nap(10000);
        }
    }

    public static void main(String[] args) {
        ExecutorService exec =
                Executors.newCachedThreadPool();
        int count = 0;
        try {
            while (true) {
                exec.execute(new Dummy());
                count++;
                method();
                if (count==1000){
                    break;
                }
            }
        } catch (Error e) {
            System.out.println(
                    e.getClass().getSimpleName() + ": " + count);
            System.exit(0);
        } finally {
            exec.shutdown();
        }
    }

    public static void method() {
//        java -XX:+PrintFlagsInitial
        long maxMemory = Runtime.getRuntime().maxMemory();//返回 Java 虚拟机试图使用的最大内存量。
        long totalMemory = Runtime.getRuntime().totalMemory();//返回 Java 虚拟机中的内存总量。
        long freeMemory = Runtime.getRuntime().freeMemory();
        System.out.println("最大内存 = " + maxMemory + "（字节）、" + (maxMemory / (double) 1024 / 1024) + "MB");
        System.out.println("内存总量 = " + totalMemory + "（字节）、" + (totalMemory / (double) 1024 / 1024) + "MB");
        System.out.println("可用内存 = " + freeMemory + "（字节）、" + (freeMemory / (double) 1024 / 1024) + "MB");
        System.out.println();
    }
}
