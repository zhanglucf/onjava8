package concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

public class CompletionStageDemo2 {
    @Test
    public void thenRun() {
        CompletableFuture<Void> cf = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenRun(() -> System.out.println("hello world"));
        cf.join();
    }

    @Test
    public void runAfterBoth() {
        //不关心这两个CompletionStage的结果，只关心这两个CompletionStage正常执行完毕，之后在进行操作（Runnable）。
        CompletableFuture<Void> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println("cf2 complete");
            new Nap(2);
            return "s1";
        }).runAfterBoth(CompletableFuture.supplyAsync(() -> {
            System.out.println("cf2 complete");
            new Nap(2);
            return "s2";
        }), () -> System.out.println("hello world"));
        new Nap(1);
        cf.join();
    }

    @Test
    public void runAfterEither() {
        //两个CompletionStage，任何一个正常完成了都会执行下一步的操作（Runnable）。
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).runAfterEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s2";
        }), () -> System.out.println("hello world"));
        while (true) {
        }
    }
}
