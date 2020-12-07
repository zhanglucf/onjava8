package chapter5;

// typeinfo/PetCount4.java

import com.example.onjava8.chapter12.Pet;
import com.example.onjava8.chapter12.Pets;

import java.util.stream.Collectors;

public class PetCount4 {
    public static void main(String[] args) {
        TypeCounter counter = new TypeCounter(Pet.class);
        Pets.stream()
                .limit(20)
                .peek(counter::count)
//                .forEach(p -> System.out.print(p.getClass().getSimpleName() + " "));
        .collect(Collectors.toList());
        System.out.println("n" + counter);
    }
}

