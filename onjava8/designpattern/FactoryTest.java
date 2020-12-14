package designpattern;

import java.util.stream.Stream;

public class FactoryTest {

    public static void test(FactoryMethod factory){
        Stream.of("Cricle","Square","Triangle")
                .map(factory::create)
                .peek(Shape::draw)
                .peek(Shape::erase)//Terminal operation
                .count();
    }
}
