package chapter8;

// onjava/BasicSupplier.java
// Supplier from a class with a no-arg constructor

import java.util.function.Supplier;
import java.util.stream.Stream;

public class BasicSupplier<T> implements Supplier<T> {
    private Class<T> type;

    public BasicSupplier(Class<T> type) {
        this.type = type;
    }

    @Override
    public T get() {
        try {
            // Assumes type is a public class:
            return type.newInstance();
        } catch (InstantiationException |
                IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    //泛型方法示例
    // Produce a default Supplier from a type token:
    public static <T> Supplier<T> create(Class<T> type) {
        return new BasicSupplier<>(type);
    }

    public static void main(String[] args) {
        Supplier<CountedObject> cos = BasicSupplier.create(CountedObject.class);
        Stream.generate(cos).limit(10).forEach(System.out::println);
    }
}

