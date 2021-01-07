package concurrent.jvm.atomic;

import concurrent.jvm.TimedAbort;

import java.util.concurrent.CompletableFuture;

/**
 * @author ZhangZhenhua
 * @date 2021/1/7 16:10
 */
public class Atomicity_6 {
    public static void test(IntTestable_4 it) {
        //防止程序一致运行下去
        new TimedAbort(6, "No failures found");
        //新开了一个异步线程，不断的增加UnsafeReturn_6类中变量i的值
        CompletableFuture.runAsync(it);
        //在主线程中不停的校验UnsafeReturn_6类中变量i的值是不是偶数
        while (true) {
            int val = it.getAsInt();
            if (val % 2 != 0) {
                System.out.println("failed with: " + val);
                System.exit(0);
            }
        }
    }
}
