package com.example.onjava8.chapter6;

// strings/Conversion.java

import java.math.*;
import java.util.*;

public class Conversion {
    public static void main(String[] args) {
        Formatter f = new Formatter(System.out);

        char u = 'a';
        System.out.println("u = 'a'");
        f.format("s: %s%n", u);
//        f.format("d: %d%n", u);//ex IllegalFormatConversionException
        f.format("c: %c%n", u);
        f.format("b: %b%n", u);
//         f.format("f: %f%n", u);//ex
//         f.format("e: %e%n", u);//ex
//         f.format("x: %x%n", u);//ex
        f.format("h: %h%n", u);

        int v = 121;
        System.out.println("v = 121");
        f.format("d: %d%n", v);
        f.format("c: %c%n", v);
        f.format("b: %b%n", v);
        f.format("s: %s%n", v);
//        f.format("f: %f%n", v);//ex
//        f.format("e: %e%n", v);//ex
        f.format("x: %x%n", v);
        f.format("h: %h%n", v);

        BigInteger w = new BigInteger("50000000000000");
        System.out.println(
                "w = new BigInteger(\"50000000000000\")");
        f.format("d: %d%n", w);
//        f.format("c: %c%n", w);//ex
        f.format("b: %b%n", w);
        f.format("s: %s%n", w);
//        f.format("f: %f%n", w);//ex
//        f.format("e: %e%n", w);//ex
        f.format("x: %x%n", w);
        f.format("h: %h%n", w);

        double x = 179.543;
        System.out.println("x = 179.543");
//        f.format("d: %d%n", x);//ex
//        f.format("c: %c%n", x);//ex
        f.format("b: %b%n", x);
        f.format("s: %s%n", x);
        f.format("f: %.3f%n", x);
        f.format("e: %e%n", x);
//        f.format("x: %x%n", x);
        f.format("h: %h%n", x);

        Conversion y = new Conversion();
        System.out.println("y = new Conversion()");

//        f.format("d: %d%n", y);//ex
//        f.format("c: %c%n", y);//ex
        f.format("b: %b%n", y);
        f.format("s: %s%n", y);
//        f.format("f: %f%n", y);//ex
//        f.format("e: %e%n", y);//ex
//        f.format("x: %x%n", y);//ex
        f.format("h: %h%n", y);

        boolean z = false;
        System.out.println("z = false");
//        f.format("d: %d%n", z);//ex
//        f.format("c: %c%n", z);//ex
        f.format("b: %b%n", z);
        f.format("s: %s%n", z);
//         f.format("f: %f%n", z);//ex
//         f.format("e: %e%n", z);//ex
//         f.format("x: %x%n", z);//ex
        f.format("h: %h%n", z);
        System.out.println("---------------");
        for (int i = 0; i < 150; i++) {
            f.format(i + ":%c%n",i);
        }
    }
}

