package com.example.onjava8.chapter9;

// arrays/MultidimensionalPrimitiveArray.java
import java.util.*;

public class MultidimensionalPrimitiveArray {
    public static void main(String[] args) {
        int[][] a = {
                { 1, 2, 3, },
                { 4, 5, 6, },
        };
        for (int[] ints : a) {
            for (int anInt : ints) {
                System.out.println(anInt);
            }
        }
        System.out.println(Arrays.deepToString(a));
    }
}

