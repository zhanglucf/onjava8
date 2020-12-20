package io.itheima;

import java.io.File;
import java.io.IOException;

public class File_1 {
    public static void main(String[] args) throws IOException {

        /**
         * 要求父目录必须存在
         * 父目录不存在则创建失败 抛出：IOException
         */
//        File file = new File("E:\\test\\a\\1.txt");
//        System.out.println("mkdir:" + file.createNewFile());

        /**
         * 父目录不存在则创建失败，return false
         * 要创建的目录已经存在，return false
         * 创建成功，return true
         * 只能创建单级目录
         */
//        File file = new File("E:\\test\\a\\b");
//        System.out.println("mkdir:" + file.mkdir());


        /**
         * 创建多级目录
         * 创建成功，return true
         * 创建失败，return false
         * 说明：如果创建E:\test\a\b\c
         *      E:\test\a\b已经存在，return true
         *      E:\test\a\b\c已经存在，return false
         */
//        File file = new File("E:\\test\\a\\b\\c");
//        System.out.println("mkdirs:" + file.mkdirs());

        /**
         * 不指定绝对路径的时候，默认路径是项目的根路径
         */
//        File file = new File("a\\demo.txt");
//        file.getParentFile().mkdirs();
//        System.out.println("mkdir:" + file.createNewFile());

        /**
         * 指定文件或者路径不存在
         * isFile&isDirectory都是false
         */
//        File file = new File("a\\b\\c");
//        System.out.println("isFile:" + file.isFile());
//        System.out.println("isDirectory:" + file.isDirectory());

        /**
         * 剪切or重命名
         */
//        File src = new File("yage.jpg");
//        File dest = new File("onjava8/io/itheima/yage.jpg");
//        System.out.println("renameTo:" + src.renameTo(dest));

        /**
         * 删除文件or文件夹
         * 如果删除的是文件夹，要求文件夹内不能有子文件子文件夹
         */
//        File file = new File("onjava8/io/itheima/aa/bb");
//        File subFile = new File("onjava8/io/itheima/aa/bb");
//        File pFile = new File("onjava8/io/itheima/aa");
//        file.mkdirs();
//        File src = new File("onjava8/io/itheima/yage.jpg");
//        src.createNewFile();
//        System.out.println("delete:" + src.delete());
//        System.out.println("pFile delete:" + pFile.delete());
//        System.out.println("subFile delete:" + subFile.delete());
//        System.out.println("pFile delete:" + pFile.delete());
    }
}
