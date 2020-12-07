package com.example.onjava8.chapter8;

// generics/Apply.java

import java.lang.reflect.*;
import java.util.*;

public class Apply {
    public static <T, S extends Iterable<T>> void apply(S seq, Method f, Object... args) {
        try {
            for (T t : seq)
                f.invoke(t, args);
        } catch (IllegalAccessException |
                IllegalArgumentException |
                InvocationTargetException e) {
            // Failures are programmer errors
            throw new RuntimeException(e);
        }
    }
}
