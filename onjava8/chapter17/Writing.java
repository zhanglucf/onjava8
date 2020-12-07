package chapter17;

// files/Writing.java
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.nio.file.*;

public class Writing {
    static Random rand = new Random(47);
    static final int SIZE = 1000;

    public static void main(String[] args) throws Exception {
        // Write bytes to a file:
        byte[] bytes = new byte[SIZE];
        rand.nextBytes(bytes);
        Files.write(Paths.get("bytes.dat"), bytes);
        System.out.println("bytes.dat: " + Files.size(Paths.get("bytes.dat")));

        // Write an iterable to a file:
        List<String> strings = Files.readAllLines(Paths.get("D:\\study\\OnJava8\\src\\main\\java\\com\\example\\onjava8\\chapter17\\ListOfLines.java"));
        List<String> lines = Files.readAllLines( Paths.get("bytes.dat"), StandardCharsets.ISO_8859_1);
        Files.write(Paths.get("Cheese.txt"), lines,StandardCharsets.ISO_8859_1);
        System.out.println("Cheese.txt: " + Files.size(Paths.get("Cheese.txt")));
    }
}
