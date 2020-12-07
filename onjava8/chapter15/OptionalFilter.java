package com.example.onjava8.chapter15;

// streams/OptionalFilter.java
import java.util.*;
import java.util.stream.*;
import java.util.function.*;
class OptionalFilter {
    static String[] elements = {
            "Foo", "", "Bar", "Baz", "Bingo"
    };
    static Stream<String> testStream() {
        return Arrays.stream(elements);
    }
    static void test(String descr, Predicate<String> pred) {
        System.out.println(" ---( " + descr + " )---");
        for(int i = 0; i <= elements.length; i++) {
            Optional<String> op = testStream()
                    .skip(i)
                    .findFirst()
                    .filter(pred);
            System.out.println(op);
        }
    }

    /**
     * 流的 filter() 会在 Predicate 返回 false 时移除流元素。
     * 而 Optional.filter() 在失败时不会删除 Optional，而是将其保留下来，并转化为空。
     *
     */
    public static void main(String[] args) {
        test("true", str -> true);
        test("false", str -> false);
        test("str != \"\"", str -> str != "");
        test("str.length() == 3", str -> str.length() == 3);
        test("startsWith(\"B\")",
                str -> str.startsWith("B"));
    }
}

