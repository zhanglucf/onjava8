package concurrent;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;

public class CompletionStageDemo1 {

    @Test
    public void demo() {

        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("要开始睡觉了");
                Thread.sleep(3000);
                System.out.println("睡醒了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("该给结果了");
            return "hello";
        });
        String result = cf.join();
        System.out.println(result);
    }
    //依赖单个阶段
    @Test
    public void thenApply() {
        CompletableFuture<String> stage = CompletableFuture.supplyAsync(() -> "hello");

        CompletableFuture<String> newStage = stage.thenApply(s -> {
            System.out.println(s);
            return  s + " world";});

        String result = newStage.join();

        System.out.println(result);
    }

    //依赖两个阶段都完成
    @Test
    public void thenCombine() {

        CompletableFuture<String> newStage = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cf1 complete");
            return "hello";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cf2 complete");
            return "world";
        }), (s1, s2) -> s1 + " " + s2);
        String result = newStage.join();
        System.out.println(result);
    }

    //依赖两个阶段中的任何一个完成
    @Test
    public void applyToEither() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cf1 complete");
            return "Tom";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cf2 complete");
            return "John";
        }), s -> "hello " + s).join();
        System.out.println(result);
    }
}
