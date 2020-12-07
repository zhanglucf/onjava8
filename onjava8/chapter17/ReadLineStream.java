package com.example.onjava8.chapter17;

// files/ReadLineStream.java
import java.nio.file.*;

public class ReadLineStream {
    public static void main(String[] args) throws Exception {
        Files.lines(Paths.get("D:\\study\\OnJava8\\src\\main\\java\\com\\example\\onjava8\\chapter17\\ReadLineStream.java"))
//                .skip(13)
                .findFirst()
                .ifPresent(System.out::println);
    }
}

