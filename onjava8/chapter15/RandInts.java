package com.example.onjava8.chapter15;

// streams/RandInts.java
import java.util.*;
import java.util.stream.*;
public class RandInts {
    private static int[] rints = new Random(47).ints(0, 1000).limit(100).toArray();
    public static IntStream rands() {
        return Arrays.stream(rints);
    }
}

