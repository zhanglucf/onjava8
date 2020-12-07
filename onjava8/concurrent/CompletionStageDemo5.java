package com.example.onjava8.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

/**
 * cfA.applyToEither(cfB,function)
 * 说明：
 *  cfA与cfB二者只有有一个先正常完成，fun可正常执行
 *  也就意味着，如果先完成的cf是异常完成，fun不会被执行
 *  whenComplete(BiConsumer<? super T, ? super Throwable> action)
 *  无论cfA还是cfB执行完成，无论成功还是失败whenComplete都会执行到，且第二个参数其实是异常对象
 */
public class CompletionStageDemo5 {

    @Test
    public void thenCombine(){
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (1 == 1) {
                throw new RuntimeException("测试一下异常情况");
            }
            return "hello ";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("return world...");
            if (1 == 1) {
                throw new RuntimeException("xxxxxxxx");
            }
            return "world";
        }), (s) -> {
            System.out.println("applyToEither指定fun执行了！ :"+s);
            return s;
        }).whenComplete((s, ex) -> {
            //BiConsumer void accept(T t, U u);
            System.out.println("current result is :" +s);
            if(ex != null){
                System.out.println("@@@@@ 阶段执行过程中存在异常：");
                ex.printStackTrace();
            }
        }).join();

        System.out.println("final result:"+result); //并不会执行
    }
}
