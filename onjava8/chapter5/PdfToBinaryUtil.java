package com.example.onjava8.chapter5;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class PdfToBinaryUtil {
    static BASE64Encoder encoder = new BASE64Encoder();
    static BASE64Decoder decoder = new BASE64Decoder();

    public static void main(String[] args) throws IOException {

        String base64String = getPDFBinary2("D:\\pdf\\java-test.pdf");
        System.out.println(base64String);
        base64StringToPDF(base64String, "1.pdf");

    }
    public static String getPDFBinary2(String filePath) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(filePath));
        return encoder.encodeBuffer(bytes).trim();
    }

    public static String getPDFBinary(String filePath) {
        File file = new File(filePath);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferInputStream = new BufferedInputStream(fileInputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BufferedOutputStream bout = new BufferedOutputStream(byteArrayOutputStream);
            byte[] buffer = new byte[1024];
            int len = bufferInputStream.read(buffer);
            while (len != -1) {
                bout.write(buffer, 0, len);
                len = bufferInputStream.read(buffer);

            }
            bout.flush();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            bufferInputStream.close();
            return encoder.encodeBuffer(bytes).trim();

        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    public static void base64StringToPDF(String base64String, String newFile) {
        try {
            byte[] bytes = decoder.decodeBuffer(base64String);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            BufferedInputStream bin = new BufferedInputStream(bais);
            File file = new File(newFile);
            FileOutputStream fout = new FileOutputStream(file);
            BufferedOutputStream bout = new BufferedOutputStream(fout);
            byte[] buffers = new byte[1024];
            int len = bin.read(buffers);
            while (len != -1) {
                bout.write(buffers, 0, len);
                len = bin.read(buffers);
            }
            bout.flush();
            bout.close();
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

}
