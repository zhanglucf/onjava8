package io.itheima;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class File_Get_2 {
    public static void main(String[] args) {
        File src = new File("onjava8/io/itheima/1.jpg");
        System.out.println("getAbsoluteFile:" + src.getAbsoluteFile());
        System.out.println("getName:" + src.getName());
        System.out.println("getParent:" + src.getParent());
        System.out.println("getPath:" + src.getPath());
        System.out.println("length:" + src.length());
        System.out.println("lastModified:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format( new Date(src.lastModified())));
    }
}
