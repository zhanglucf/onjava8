package com.example.onjava8.chapter8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenericVarargs {

    @SafeVarargs
    private static <T> List<T> getList(T...param){
        List<T> ts = new ArrayList<>();
        for (T t : param) {
            ts.add(t);
        }
        return ts;
    }

    public static void main(String[] args) {
        List<Integer> il = GenericVarargs.getList(1, 2, 3);
        List<String> sl = GenericVarargs.getList("a", "b", "c");
        il.forEach(System.out::println);
        sl.forEach(System.out::println);
    }
}
