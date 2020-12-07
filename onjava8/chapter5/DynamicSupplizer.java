package com.example.onjava8.chapter5;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class DynamicSupplizer<T> implements Supplier {
    private Class<T> type;

    public DynamicSupplizer(Class<T> type) {
        this.type = type;
    }

    @Override
    public T get() {
        try {
            return type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Stream.generate(new DynamicSupplizer<>(CountedInteger.class))
                .skip(3)
                .limit(10)
                .forEach(System.out::println);
    }
}
