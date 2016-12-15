package com.tangzhixiong.cruncher;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage:");
            System.err.println("    java -jar cruncher.jar <INPUT_FILE>");
            System.err.println("    java -jar cruncher.jar <INPUT_FILE> <OUTPUT_FILE>");
            System.exit(1);
        }
        String inputPath = args[0];
        String outputPath = "";
        if (args.length >= 2) {
            outputPath = args[1];
        } else {
            if (inputPath.endsWith(".bin")) {
                outputPath = inputPath.substring(0, inputPath.length()-4);
            } else {
                outputPath = inputPath + ".bin";
            }
        }
        File inputFile = new File(inputPath);
        File outputFile = new File(outputPath);
        try (
                FileInputStream fis = new FileInputStream(inputFile);
                FileOutputStream fos = new FileOutputStream(outputFile);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ) {
            byte[] buf = new byte[1024];
            int readNum = -1;
            while ((readNum = fis.read(buf)) != -1) {
                for (int i = 0; i < readNum; i++) {
                    buf[i] ^= 0xf0;
                }
                bos.write(buf, 0, readNum);
            }
            byte[] bytes = bos.toByteArray();
            fos.write(bytes);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
