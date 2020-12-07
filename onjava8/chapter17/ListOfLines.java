package com.example.onjava8.chapter17;

// files/ListOfLines.java
import java.util.*;
import java.nio.file.*;

public class ListOfLines {
    public static void main(String[] args) throws Exception {
        Files.readAllLines(Paths.get("D:\\study\\OnJava8\\src\\main\\java\\com\\example\\onjava8\\chapter17\\ListOfLines.java"))
                .stream()
                .filter(line -> !line.startsWith("//"))
//                .map(line ->
//                        line.substring(0, line.length()/2))
                .forEach(System.out::println);
    }
}

