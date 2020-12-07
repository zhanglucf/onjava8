package com.example.onjava8.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

public class CompletionStageDemo6 {

    @Test
    public void handle() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //出现异常
            if (1 == 1) {
                throw new RuntimeException("测试一下异常情况");
            }
            return "Tom";
        }).handle((s, t) -> {
            if (t != null) { //出现异常了
                return "John";
            }
            return s; //这里也可以对正常结果进行转换
        }).join();
        System.out.println(result);
    }

}
