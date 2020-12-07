package chapter15;

import java.util.Optional;
import java.util.stream.Stream;

public class OptionalBasics {
    static void test(Optional<String> optional){
        if (optional.isPresent()) {
            System.out.println(optional.get());
        }else {
            System.out.println("Nothing inside!");
        }
    }

    public static void main(String[] args) {
        test(Stream.<String>empty().findFirst());
        System.out.println("-------------------");
        test(Stream.of("hello","java","!").findAny());

    }
}
