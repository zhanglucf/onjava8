package designpattern;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ShapeFactory3_2<T> implements Supplier<T> {
    private final static Random rand = new Random();
    private final Supplier<? extends T>[] suppliers;

    public ShapeFactory3_2(Supplier<T>... suppliers) {
        this.suppliers = suppliers;
    }

    @Override
    public T get() {
        return suppliers[rand.nextInt(suppliers.length)].get();
    }



    public static void main(String[] args) {
        ShapeFactory3_2<Shape> factory3 = new ShapeFactory3_2(Cricle::new, Square::new, Triangle::new);
        Stream.generate(factory3)
                .limit(100)
                .peek(Shape::draw)
                .peek(Shape::erase)
                .count();
    }

}
