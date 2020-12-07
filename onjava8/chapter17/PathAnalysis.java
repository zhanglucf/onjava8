package com.example.onjava8.chapter17;

// files/PathAnalysis.java

import java.nio.file.*;
import java.io.IOException;

public class PathAnalysis {
    static void say(String id, Object result) {
        System.out.print(id + ": ");
        System.out.println(result);
    }

    //D:\study\OnJava8\src\main\java\com\example\onjava8\chapter17\PathAnalysis.java
//D:\study\OnJava8\src\main\java\com\example\onjava8\chapter17
//D:\study\OnJava8\src\main\java\com\example\onjava8\chapter17\PathAnalysis.java
    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("os.name"));
        Path p = Paths.get("src", "main", "java", "com", "example", "onjava8", "chapter17", "PathAnalysis.java");
        say("path", p);
        p = p.toAbsolutePath();
        say("absolutePath", p);
        say("Exists", Files.exists(p));
        say("Directory", Files.isDirectory(p));
        say("Executable", Files.isExecutable(p));
        say("Readable", Files.isReadable(p));
        say("RegularFile", Files.isRegularFile(p));
        say("Writable", Files.isWritable(p));
        say("notExists", Files.notExists(p));
        say("Hidden", Files.isHidden(p));
        say("size", Files.size(p));
        say("FileStore", Files.getFileStore(p));
        say("LastModified: ", Files.getLastModifiedTime(p));
        say("Owner", Files.getOwner(p));
        say("ContentType", Files.probeContentType(p));
        say("SymbolicLink", Files.isSymbolicLink(p));
        if (Files.isSymbolicLink(p))
            say("SymbolicLink", Files.readSymbolicLink(p));
        if (FileSystems.getDefault().supportedFileAttributeViews().contains("posix"))
            say("PosixFilePermissions",
                    Files.getPosixFilePermissions(p));
    }
}


