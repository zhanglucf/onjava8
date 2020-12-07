package com.example.onjava8.chapter21;

// enums/cartoons/EnumImplementation.java
// An enum can implement an interface
// {java enums.cartoons.EnumImplementation}
import java.util.*;
import java.util.function.*;
enum CartoonCharacter implements Supplier<CartoonCharacter> {
    SLAPPY, SPANKY, PUNCHY,SILLY, BOUNCY, NUTTY, BOB;
    private Random rand = new Random(47);
    @Override
    public CartoonCharacter get() {
        return values()[rand.nextInt(values().length)];
    }
}
public class EnumImplementation {
    public static <T> void printNext(Supplier<T> rg) {
        System.out.print(rg.get() + ", ");
    }
    public static void main(String[] args) {
// Choose any instance:
        CartoonCharacter cc = CartoonCharacter.BOB;
        for(int i = 0; i < 10; i++)
            printNext(cc);
    }
}
