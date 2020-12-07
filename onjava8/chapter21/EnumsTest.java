package com.example.onjava8.chapter21;

public class EnumsTest {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(Enums.random(OzWitch.class));
        }
    }
}
