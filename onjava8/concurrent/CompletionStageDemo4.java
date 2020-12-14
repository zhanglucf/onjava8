package concurrent;


import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

public class CompletionStageDemo4 {

    @Test
    public void thenCombine(){
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (1 == 1) {
                throw new RuntimeException("测试一下异常情况");
            }
            return "hello ";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("return world...");  //会执行
            return "world";
        }), (s1, s2) -> {
            String s = s1 + " " + s2;   //并不会执行
            System.out.println("combine result :"+s); //并不会执行
            return s;
        }).whenComplete((s, ex) -> {
            System.out.println("current result is :" +s);
            if(ex != null){
                System.out.println("阶段执行过程中存在异常：");
                ex.printStackTrace();
            }
        }).join();

        System.out.println("final result:"+result); //并不会执行
    }
}
