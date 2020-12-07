package com.example.onjava8.chapter21;

import java.util.Random;

public class Enums<T> {
    private static Random r = new Random(47);

    //古怪的语法<T extends Enum<T>> 表示 T 是一个 enum 实例
    public static <T extends Enum<T>> T random(Class<T> clazz){
        T[] enums = clazz.getEnumConstants();
        return enums[r.nextInt(enums.length)];
    }

    public static <T>  T random(T[] values){
        return values[r.nextInt(values.length)];
    }
}
