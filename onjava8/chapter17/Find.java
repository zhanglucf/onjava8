package com.example.onjava8.chapter17;

// files/Find.java
// {ExcludeFromGradle}

import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

public class Find {
    private static long count = 0;
    public static void main(String[] args) throws Exception {
//        Path test = Paths.get("test");
//        Directories.refreshTestDir();
//        Directories.populateTestDir();
//        // Creating a *directory*, not a file:
//        Files.createDirectory(test.resolve("dir.tmp"));
//
//        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/*.{tmp,txt}");
//        Files.walk(test)
//                .filter(matcher::matches)
//                .forEach(System.out::println);
//        System.out.println("***************");
//
//        PathMatcher matcher2 = FileSystems.getDefault()
//                .getPathMatcher("glob:*.tmp");
//        Files.walk(test)
//                .map(Path::getFileName)
//                .filter(matcher2::matches)
//                .forEach(System.out::println);
//        System.out.println("***************");
//        Files.walk(test) // Only look for files
//                .filter(Files::isRegularFile)
//                .map(Path::getFileName)
//                .filter(matcher2::matches)
//                .forEach(System.out::println);
        System.out.println("文件查找开始");
        long l = System.currentTimeMillis();
        Path p = Paths.get("C:\\Program Files (x86)");
        PathMatcher matcher3 = FileSystems.getDefault()
                .getPathMatcher("glob:*License.txt");
        Files.walk(p)
                .filter(x -> {
                    count ++;
                    return Files.isRegularFile(x) && matcher3.matches(x.getFileName());
                })
                .forEach(System.out::println);
        System.out.println("文件查找结束,共查找"+count+"个文件，用时：" + (System.currentTimeMillis() - l));
    }
}

