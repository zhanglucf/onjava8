package io;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * D:\tmp\io\GZIPcompress.java
 * 在tmp目录下执行javac io\GZIPcompress.java （生成class文件）
 *   在tmp目录下执行Java io.GZIPcompress （class文件正常执行）
 *   在io目录下执行Java io.GZIPcompress （错误: 找不到或无法加载主类 GZIPcompress）
 *
 * 在D:目录下执行javac tmp\io\GZIPcompress.java （生成class文件）
 *     在tmp目录下执行Java io.GZIPcompress （class文件正常执行）
 *     在io目录下执行Java io.GZIPcompress （错误: 找不到或无法加载主类 GZIPcompress）
 * 关于javac 与 java 两个命令的结论
 *  javac和命令窗口位置无关 可以使用绝对路径
 *  java 命令窗口必须在包的上级目录下，例如包是com.io 那么java命令需要在com的父目录下执行
 *
 *  如果没有包，则
 */
public class GZIPcompress {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(
                    "Usage: \nGZIPcompress file\n" +
                            "\tUses GZIP compression to compress " +
                            "the file to test.gz");
            System.exit(1);
        }
        try (
                InputStream in = new BufferedInputStream(
                        new FileInputStream(args[0]));
                BufferedOutputStream out =
                        new BufferedOutputStream(
                                new GZIPOutputStream(
                                        new FileOutputStream("test.gz")))
        ) {
            System.out.println("Writing file");
            int c;
            while ((c = in.read()) != -1)
                out.write(c);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Reading file");
        try (
                BufferedReader in2 = new BufferedReader(
                        new InputStreamReader(new GZIPInputStream(
                                new FileInputStream("test.gz"))))
        ) {
            in2.lines().forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
