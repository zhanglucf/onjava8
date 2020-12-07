package com.example.onjava8.chapter5;

// typeinfo/pets/ForNameCreator.java

import com.example.onjava8.chapter12.Pet;
import com.example.onjava8.chapter12.PetCreator;

import java.util.*;

public class ForNameCreator extends PetCreator {
    private static List<Class<? extends Pet>> types = new ArrayList<>();
    // 需要随机生成的类型名:
    private static String[] typeNames = {
            "com.example.onjava8.chapter12.Mutt",
            "com.example.onjava8.chapter12.Pug",
            "com.example.onjava8.chapter12.EgyptianMau",
            "com.example.onjava8.chapter12.Manx",
            "com.example.onjava8.chapter12.Cymric",
            "com.example.onjava8.chapter12.Rat",
            "com.example.onjava8.chapter12.Mouse",
            "com.example.onjava8.chapter12.Hamster"
    };

    @SuppressWarnings("unchecked")
    private static void loader() {
        try {
            for (String name : typeNames) {
                types.add((Class<? extends Pet>) Class.forName(name));
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        loader();
    }

    @Override
    public List<Class<? extends Pet>> types() {
        return types;
    }
}
