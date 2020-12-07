package chapter17;

// files/Directories.java

import java.util.*;
import java.nio.file.*;

public class Directories {
    static Path test = Paths.get("test");
    static String sep = FileSystems.getDefault().getSeparator();
    static List<String> parts = Arrays.asList("f1", "f2","f3");

    static Path makeVariant() {
        Collections.rotate(parts, 1);
        String str = String.join(sep, parts);
        System.out.println("新生成的路径： " + str);
        return Paths.get("test", str);
    }

    static void refreshTestDir() throws Exception {
        if (Files.exists(test)){
            System.out.println("文件或者目录存在,先删除已存在的 " + test);
            RmDir.rmdir(test);
        }
        if (!Files.exists(test)) {
            System.out.println("文件或者目录不存在,新建 " + test);
            Files.createDirectory(test);
        }
    }

    public static void main(String[] args) throws Exception {
        //这个名字命名的可以学习下
        refreshTestDir();
        Files.createFile(test.resolve("Hello.txt"));
        Path variant = makeVariant();
        // Throws exception (too many levels):
        try {
            //创建目录，如果创建多级目录，会报错
            Files.createDirectory(variant);
        } catch (Exception e) {
            System.out.println("Nope, that doesn't work.");
        }
        populateTestDir();
        Path tempdir = Files.createTempDirectory(test, "DIR_");
        //创建临时文件
        Files.createTempFile(tempdir, "pre", ".non");
        //获取当前目录下文件流
        Files.newDirectoryStream(test).forEach(System.out::println);
        System.out.println("*********");
        //获取当前目录下以及子目录文件流
        Files.walk(test).forEach(System.out::println);
    }

    static void populateTestDir() throws Exception {
        for (int i = 0; i < parts.size(); i++) {
            Path variant = makeVariant();
            System.out.println("旋转生成新的path " + variant);
            if (!Files.exists(variant)) {
                System.out.println("path如果不存在，就新建 " + variant);
                //递归创建目录
                Files.createDirectories(variant);
                System.out.println("将指定路径下的Directories.java文件copy到上一步新建的目录下");
                Files.copy(Paths.get("src", "main", "java", "com", "example", "onjava8", "chapter17", "Directories.java"),variant.resolve("File.txt"));
                System.out.println("创建临时目录");
                Files.createTempFile(variant, null, null);
            }
        }
    }
}


