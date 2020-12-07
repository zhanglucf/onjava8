package com.example.onjava8.chapter6;

// strings/UsingStringBuilder.java

import java.time.Instant;
import java.util.*;
import java.util.stream.*;
public class UsingStringBuilder {
    public static String string1() {
        long start = Instant.now().getEpochSecond();
        Random rand = new Random(47);
        StringBuilder result = new StringBuilder("[");
        for(int i = 0; i < 25000000; i++) {
            result.append(rand.nextInt(100));
            result.append(", ");
        }
        result.delete(result.length()-2, result.length());
        result.append("]");
        System.out.println("string1 耗时：" + (Instant.now().getEpochSecond() - start ) + "秒");
        return result.toString();
    }
    public static String string2() {
        long start = Instant.now().getEpochSecond();
        String result = new Random(47)
                .ints(25000000, 0, 100)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(", "));
        System.out.println("string2 耗时：" + (Instant.now().getEpochSecond() - start ) + "秒");
        return "[" + result + "]";
    }
    public static String string3() {
        long start = Instant.now().getEpochSecond();
        Random rand = new Random(47);
        String result = "[";
        for(int i = 0; i < 25000; i++) {
            result += (rand.nextInt(100));
            if(i<24){
                result += " ,";
            }else {
                result += "]";
            }
        }
        System.out.println("string3 耗时：" + (Instant.now().getEpochSecond() - start ) + "秒");
        return result.toString();
    }
    public static void main(String[] args) {
        string1();
        string2();
        string3();
    }
}
