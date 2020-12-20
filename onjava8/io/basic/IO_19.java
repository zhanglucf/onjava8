package io.basic;

import java.io.*;

/**
 * 复制整个目录下所有文件到指定目录下
 */
public class IO_19 {
    public static void main(String[] args) throws IOException {

        copyFolder(new File("E:\\Temp\\io")
                , new File("E:\\test"));
    }

    static void copyFolder(File srcFolder, File destFolder) {
        if (!destFolder.exists()) {
            destFolder.mkdirs();
        }
        if (srcFolder.isFile()) {
            copyFile(srcFolder, new File(destFolder, srcFolder.getName()));
        } else {
            //如果是目录，先在destFolder下创建文件夹
            File subFolder = new File(destFolder, srcFolder.getName());
            subFolder.mkdir();
            for (File file : srcFolder.listFiles()) {
                copyFolder(file, subFolder);
            }
        }
    }


    /**
     * @param srcFile  需要copy的文件
     * @param destFile 目标文件
     */
    static void copyFile(File srcFile, File destFile) {
        try (
                FileInputStream in = new FileInputStream(srcFile);
                FileOutputStream out = new FileOutputStream(destFile);
        ) {
            int len;
            byte[] bys = new byte[1024];
            while ((len = in.read(bys)) != -1) {
                out.write(bys,0,len);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
