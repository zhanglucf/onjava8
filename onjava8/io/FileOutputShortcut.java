package io;

// iostreams/FileOutputShortcut.java
// {VisuallyInspectOutput}
import java.io.*;

public class FileOutputShortcut {
    static String file = "FileOutputShortcut.dat";

    public static void main(String[] args) {
        try (
                BufferedReader in = new BufferedReader(
                        new StringReader(BufferedInputFile.read(
                                "onjava8\\io\\FileOutputShortcut.java")));
                // Here's the shortcut:
                PrintWriter out = new PrintWriter(file)
        ) {
            in.lines()
                    .peek(System.out::println)
                    .forEach(out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(BufferedInputFile.read(file));
    }
}

