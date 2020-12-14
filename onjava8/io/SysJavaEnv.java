package io;

import sun.util.calendar.ZoneInfo;

import java.time.LocalDateTime;

public class SysJavaEnv {
    public static void main(String[] args) {
        System.out.println("------------ JAVA ENV ------------");
        System.out.println(LocalDateTime.now());
        System.out.println("Default Zone DisplayName " + ZoneInfo.getDefault().getDisplayName());
        System.out.println("Default Zone ID " + ZoneInfo.getDefault().getID());
        System.out.println("availableProcessors " + Runtime.getRuntime().availableProcessors());
        System.out.println("JVM maxMemory " + Runtime.getRuntime().maxMemory() / (1024 * 1024) + " MB");
        System.out.println("JVM free memory " + Runtime.getRuntime().freeMemory() / (1024 * 1024) + " MB");
        System.out.println();
        System.out.println("file.encoding " + System.getProperty("file.encoding"));
        System.out.println("user.name " + System.getProperty("user.name"));
        System.out.println("java.home " + System.getProperty("java.home"));
        System.out.println("java.version " + System.getProperty("java.version"));
        System.out.println("file.separator " + System.getProperty("file.separator"));
        System.out.println("user.dir " + System.getProperty("user.dir"));
        System.out.println("line.separator " + System.getProperty("line.separator"));
        System.out.format("-------------------------------");
    }
}
