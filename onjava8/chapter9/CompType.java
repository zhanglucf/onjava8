package com.example.onjava8.chapter9;

// arrays/CompType.java
// Implementing Comparable in a class

import java.util.Arrays;
import java.util.SplittableRandom;

import static com.example.onjava8.chapter9.ArrayShow.show;

public class CompType implements Comparable<CompType> {
    private static int count = 1;
    private static SplittableRandom r = new SplittableRandom(47);
    int i;
    int j;

    public CompType(int n1, int n2) {
        i = n1;
        j = n2;
    }

    public static CompType get() {
        return new CompType(r.nextInt(100), r.nextInt(100));
    }

    public static void main(String[] args) {
        CompType[] a = new CompType[12];
        Arrays.setAll(a, n -> get());
        show("Before sorting", a);
        Arrays.sort(a);
        show("After sorting", a);
    }

    @Override
    public String toString() {
        String result = "[i = " + i + ", j = " + j + "]";
        if (count++ % 3 == 0) result += "\n";
        return result;
    }

    @Override
    public int compareTo(CompType rv) {
        System.out.print("this" +this);
        System.out.print("|rv" + rv +"\r");
        return (this.i < rv.i ? -1 : (this.i == rv.i ? 0 : 1));
    }
}

