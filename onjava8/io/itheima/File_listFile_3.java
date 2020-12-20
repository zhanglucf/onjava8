package io.itheima;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class File_listFile_3 {
    public static void main(String[] args) {
        /**
         * 写一个方法，查询指定目录下所有以jpg结尾的图片
         */
        String path = "D:\\onjava8";
        print(path);
    }

    static void print(String path){
        File file = new File(path);
        if (file.exists()) {
            if (file.isFile()) {
                if (file.getName().endsWith(".jpg")) {
                    System.out.println(file.getName());
                }
            }else {
                for (File subFile : file.listFiles()) {
                    print(subFile.getAbsolutePath());
                }
            }
        }else {
        System.out.println("指定的目录不存在");
        }
    }
}
