package com.example.onjava8.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

public class CompletionStageDemo3 {
    @Test
    public void thenCompose(){
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenCompose(s -> CompletableFuture.supplyAsync(() -> {
            System.out.println(s);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return s + " world";
        })).join();

        System.out.println(result);
    }
}
