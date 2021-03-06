package chapter12;

// collections/UniqueWords.java
import java.util.*;
import java.nio.file.*;

public class UniqueWords {
    public static void
    main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("D:\\study\\OnJava8\\src\\main\\java\\com\\example\\onjava8\\chapter12\\UniqueWords.java"));
        Set<String> words = new TreeSet<>();
        for(String line : lines)
            for(String word : line.split("\\W+"))
                if(word.trim().length() > 0)
                    words.add(word);
        System.out.println(words);
    }
}

