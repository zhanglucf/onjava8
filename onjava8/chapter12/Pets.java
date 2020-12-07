package com.example.onjava8.chapter12;

// typeinfo/pets/Pets.java
// Facade to produce a default PetCreator
import com.example.onjava8.chapter5.ForNameCreator;
import com.example.onjava8.chapter5.LiteralPetCreator;

import java.util.*;
import java.util.stream.*;

public class Pets {
//    public static final PetCreator CREATOR = new LiteralPetCreator();
    public static final PetCreator CREATOR = new ForNameCreator();

    public static Pet get() {
        return CREATOR.get();
    }

    public static Pet[] array(int size) {
        Pet[] result = new Pet[size];
        for (int i = 0; i < size; i++)
            result[i] = CREATOR.get();
        return result;
    }

    public static List<Pet> list(int size) {
        List<Pet> result = new ArrayList<>();
        Collections.addAll(result, array(size));
        return result;
    }

    public static Stream<Pet> stream() {
        return Stream.generate(CREATOR);
    }

    public static void main(String[] args) {
        System.out.println(Integer.class.isAssignableFrom(Object.class));
        System.out.println(Object.class.isAssignableFrom(Object.class));
        System.out.println(Object.class.isAssignableFrom(Integer.class));
        System.out.println(Integer.class.isAssignableFrom(Integer.class));
    }
}

