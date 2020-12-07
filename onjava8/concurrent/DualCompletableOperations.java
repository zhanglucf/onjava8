package com.example.onjava8.concurrent;

// concurrent/DualCompletableOperations.java

import java.util.concurrent.CompletableFuture;

import static com.example.onjava8.concurrent.CompletableUtilities.*;

public class DualCompletableOperations {
    static CompletableFuture<Workable> cfA, cfB;

    static void init() {
        //need 2 second
        cfA = Workable.make("A", 1);
        //need 3 second
        cfB = Workable.make("B", 2); // Always wins
    }

    static void join() {
        cfA.join();
        cfB.join();
        System.out.println("*****************");
    }

    public static void main(String[] args) {
        init();
        //cfA cfB 只有要有一个正常完成 fun就会被执行
        voidr(cfA.runAfterEitherAsync(cfB, () -> System.out.println("runAfterEither")));
        //output Workable[BW] 阻塞直到 cfA和cfB都完成
        join();
        //重新生成cfA cfB
        init();
        //cfA 和cfB 都有执行成功后 才调用fun
        voidr(cfA.runAfterBothAsync(cfB, () -> System.out.println("runAfterBoth")));

        join();

        init();

        showr(cfA.applyToEitherAsync(cfB, w -> {
            System.out.println("applyToEither: " + w);
            return w;
        }));
        //cfB 没有输出结果？？？
        join();

        init();

        voidr(cfA.acceptEitherAsync(cfB, w -> {
            System.out.println("acceptEither: " + w);
        }));
        join();

        init();

        voidr(cfA.thenAcceptBothAsync(cfB, (w1, w2) -> {
            System.out.println("thenAcceptBoth: "
                    + w1 + ", " + w2);
        }));
        join();

        init();

        showr(cfA.thenCombineAsync(cfB, (w1, w2) -> {
            System.out.println("thenCombine: "
                    + w1 + ", " + w2);
            return w1;
        }));

        join();

        init();

        CompletableFuture<Workable>
                cfC = Workable.make("C", 0.08),
                cfD = Workable.make("D", 0.09);
        CompletableFuture.anyOf(cfA, cfB, cfC, cfD)
                .thenRunAsync(() ->
                        System.out.println("anyOf"));
        join();

        init();

        cfC = Workable.make("C", 0.08);
        cfD = Workable.make("D", 0.09);

        CompletableFuture.allOf(cfA, cfB, cfC, cfD)
                .thenRunAsync(() ->
                        System.out.println("allOf"));

        join();
    }
}

