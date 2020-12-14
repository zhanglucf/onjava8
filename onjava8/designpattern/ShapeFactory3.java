package designpattern;

import util.Print;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static util.Print.println;

public class ShapeFactory3 implements Supplier<Shape> {
    private final static Random rand = new Random();
    private final Supplier<Shape>[] suppliers;

    public ShapeFactory3(Supplier<Shape>... suppliers) {
        this.suppliers = suppliers;
    }

    @Override
    public Shape get() {
        return suppliers[rand.nextInt(suppliers.length)].get();
    }

    public static void main(String[] args) {
        ShapeFactory3 factory3 =
                new ShapeFactory3(Cricle::new, Square::new, Triangle::new);

        Stream.generate(factory3)
                .limit(100)
                .peek(Shape::draw)
                .peek(Shape::erase)
                .count();
    }

}
