package com.example.onjava8.chapter17;

// files/StreamInAndOut.java
import java.io.*;
import java.nio.file.*;
import java.util.stream.*;

public class StreamInAndOut {
    public static void main(String[] args) {
        try(
                Stream<String> input =
                        Files.lines(Paths.get("D:\\study\\OnJava8\\src\\main\\java\\com\\example\\onjava8\\chapter17\\StreamInAndOut.java"));
                PrintWriter output =
                        new PrintWriter("StreamInAndOut.txt")
        ) {
            input.map(String::toUpperCase)
                    .forEachOrdered(output::println);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}

