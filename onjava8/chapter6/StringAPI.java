package com.example.onjava8.chapter6;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringAPI {
    public static void main(String[] args) {
        String s = "ABCDEFG";
        for (int i = 0; i < 7; i++) {
            System.out.println("charAt(" + i + ")=" + s.charAt(i));
        }
        byte[] bytes = s.getBytes();
        System.out.println("\"ABCDEFG\".getBytes()" + Arrays.toString(bytes));
        System.out.println("\"ABCDEFG\".toCharArray()=" + Arrays.toString(s.toCharArray()));
        System.out.println("\"A\".compareTo(\"B\")=" + ("A".compareTo("B")));
        System.out.println("\"ABCDEFG\".contentEquals(new StringBuilder(\"ABCDEFG\"))=" + s.contentEquals(new StringBuilder("ABCDEFG")));
        System.out.println("\"abcdefgijk\".regionMatches(2,\"cdefg\",0,3)=" + "abcdefgijk".regionMatches(2, "cdefg", 0, 3));
        System.out.println("String.join(\",\",\"b\",\"c\",\"a\")" + String.join(",", "b", "c", "a"));
        System.out.println("\"ab\".concat(\"cd\")=" + "ab".concat("cd"));
    }
}
